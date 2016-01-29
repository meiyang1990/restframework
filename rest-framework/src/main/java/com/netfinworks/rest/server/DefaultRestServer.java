/**
 * 
 */
package com.netfinworks.rest.server;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.netfinworks.rest.annotation.AuditAnnotationData;
import com.netfinworks.rest.annotation.AuthorizeAnnotationData;
import com.netfinworks.rest.annotation.RenderAnnotationData;
import com.netfinworks.rest.audit.AuditResult;
import com.netfinworks.rest.audit.IResourceAudit;
import com.netfinworks.rest.auth.AuthCheckResult;
import com.netfinworks.rest.auth.IAuthCheck;
import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.exception.ResourceException;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.render.IRender;
import com.netfinworks.rest.util.AnnotationUtil;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;
import com.netfinworks.rest.util.UriTemplate;
import com.netfinworks.rest.util.UrlMatchKind;

/**
 * @author bigknife
 * 
 */
public class DefaultRestServer implements IRestServer, ApplicationContextAware {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private ApplicationContext applicationContext;

	private IHttpVerbInvoker httpVerbInvoker;

	private Map<String, Object> noVariableResourceMap = new ConcurrentHashMap<String, Object>();
	private Map<String, Object> withVariableResourceMap = new ConcurrentHashMap<String, Object>();
	private Map<String, UriTemplate> uriTemplateMap = new ConcurrentHashMap<String, UriTemplate>();
	private Map<String, ResourceMethodMeta> resOperateMap = new ConcurrentHashMap<String, ResourceMethodMeta>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netfinworks.rest.server.IRestServer#registerWebResource(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void registerWebResource(String url, UrlMatchKind matchKind, Object bean) {
		UriTemplate uriTemplate = new UriTemplate(url, matchKind);
		if (uriTemplate.getVariableNames().isEmpty()) {
			noVariableResourceMap.put(url, bean);
			logger.debug("found no variable resource: {}", url);
		} else {
			withVariableResourceMap.put(url, bean);
			uriTemplateMap.put(url, new UriTemplate(url, matchKind));
			logger.debug("found with variable resource: {}", url);
		}

		cacheResourceOperation(url, bean);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netfinworks.rest.server.IRestServer#init()
	 */
	@Override
	public void init() {
		initHttpVerbInvoker();
	}

	private void initHttpVerbInvoker() {
		httpVerbInvoker = new DefaultHttpVerbInvoker();
		if (httpVerbInvoker instanceof ApplicationContextAware) {
			((ApplicationContextAware) httpVerbInvoker)
					.setApplicationContext(applicationContext);
		}
		if (httpVerbInvoker instanceof UriTemplateMapAware) {
			((UriTemplateMapAware) httpVerbInvoker)
					.setUrlTemplateMap(uriTemplateMap);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netfinworks.rest.server.IRestServer#destroy()
	 */
	@Override
	public void destroy() {
		noVariableResourceMap.clear();
		withVariableResourceMap.clear();
		uriTemplateMap.clear();
		resOperateMap.clear();
	}

	@Override
	public Response serve(Request restRequest) {
		// 根据request ，匹配出资源
		Object resourceObj = findResourceAndSetUrlTemplate(restRequest);
		if (resourceObj != null) {
			ResourceMethodMeta rmm = resOperateMap.get(getCacheKey(
					restRequest.getUrlTemplate(), restRequest.getHttpVerb()));
			// 调用相应的方法
			if (rmm == null) {
				logger.warn(
						"resource method not found,url = {}, method = {}",
						new Object[] { restRequest.getUrl(),
								restRequest.getHttpVerb() });
				return buildNotFoundResponse();
			}
			try {
				// 授权检查
				AuthorizeAnnotationData aad = AnnotationUtil
						.getAuthorizedAnnotationData(rmm);
				if (aad != null) {
					AuthCheckResult authCheckResult = checkResourceAuth(
							restRequest.getHeaders(), aad);
					if (!authCheckResult.isPassed()) {
						return authCheckResult.getImmutableResponse();
					}
				}
				// 访问审计
				AuditAnnotationData auditData = AnnotationUtil
						.getAuditAnnotationData(rmm);
				if (auditData != null) {
					AuditResult auditResult = audit(restRequest, auditData);
					if (!auditResult.isCompliant()) {
						return auditResult.getImmutableResponse();
					}
				}

				logger.debug("call resource {} to deal.",rmm);
				Object result = httpVerbInvoker.invoke(restRequest, rmm);
				logger.debug("resource operation result : {}", result);

				// 封装返回结果
				IRender render = findRender(rmm);
				if (render != null) {
					return render.render(result, restRequest);
				} else {
					logger.error(
							"no render found for resource operation:{} {}",
							new Object[] { rmm.getResource(), rmm.getMethod() });
				}
			} catch (Throwable e) {
				// 系统异常，build error response
				logger.error("resource handling exception!", e);
				IRender exceptionRender = findExceptionRender(rmm);
				return buildErrorResponse(exceptionRender, restRequest, e);
			}
			// never happen
			return null;
		} else {
			// handle No resource found error
			logger.warn(
					"resource not found,url = {}, method = {}",
					new Object[] { restRequest.getUrl(),
							restRequest.getHttpVerb() });
			return buildNotFoundResponse();
		}
	}

	private AuditResult audit(Request restRequest, AuditAnnotationData aad)
			throws BeansException, InstantiationException,
			IllegalAccessException {
		String beanName = aad.getAuditRef();
		Class<? extends IResourceAudit> clz = aad.getAudit();
		IResourceAudit audit = Magic.EmtpyString.equals(beanName) ? clz
				.newInstance() : (IResourceAudit) applicationContext
				.getBean(beanName);
		return audit.audit(restRequest.getUrl(), restRequest.getUrlTemplate(),
				restRequest.getHttpVerb(), restRequest.getHeaders());
	}

	private AuthCheckResult checkResourceAuth(Map<String, String> headers,
			AuthorizeAnnotationData aad) throws Exception {
		String checkRef = aad.getCheckRef();

		if (!Magic.EmtpyString.equals(checkRef)
				&& applicationContext.containsBean(checkRef)) {
			IAuthCheck check = (IAuthCheck) applicationContext
					.getBean(checkRef);
			return check.checkAuth(headers);
		}

		Class<? extends IAuthCheck> checkClass = aad.getCheck();
		IAuthCheck check = checkClass.newInstance();
		return check.checkAuth(headers);
	}

	private Response buildErrorResponse(IRender render, Request restRequest,
			Throwable t) {
		if (render != null) {
			return render
					.renderException(
							(t instanceof InvocationTargetException) ? ((InvocationTargetException) t)
									.getTargetException() : t, restRequest);
		} else {
			if (t instanceof InvocationTargetException) {
				InvocationTargetException ite = (InvocationTargetException) t;
				logger.error("resource exception!", ite.getTargetException());
				Throwable e = ite.getTargetException();
				if (e instanceof ResourceException) {
					return ((ResourceException) e).getExceptionResponse();
				}
			}
			Response response = new Response();
			response.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
			byte[] msgArray = Encoding
					.decode(((t instanceof InvocationTargetException) ? ((InvocationTargetException) t)
							.getTargetException() : t).getMessage());
			if (msgArray != null) {
				response.setInputStream(new ByteArrayInputStream(msgArray));
			}
			response.addHeader(HttpHeaderName.ContentType, ContentType.Txt);
			return response;
		}

	}

	private IRender findRender(ResourceMethodMeta rmm) throws BeansException,
			InstantiationException, IllegalAccessException {
		RenderAnnotationData rad = AnnotationUtil
				.getWebResourceRenderAnnotationData(rmm);
		return (IRender) (rad == null ? null : (Magic.EmtpyString.equals(rad
				.getRenderRef()) ? rad.getRender().newInstance()
				: applicationContext.getBean(rad.getRenderRef())));
	}

	private IRender findExceptionRender(ResourceMethodMeta rmm){
		RenderAnnotationData rad = AnnotationUtil
				.getWebResourceRenderAnnotationData(rmm);
		try {
			return (IRender) (rad == null ? null : (Magic.EmtpyString.equals(rad
					.getExceptionRenderRef()) ? rad.getExceptionRender().newInstance()
					: applicationContext.getBean(rad.getExceptionRenderRef())));
		} catch (Exception e) {
			logger.error("find exception render error",e);
			return null;
		} 
	}

	private Response buildNotFoundResponse() {
		Response noResource = new Response();
		noResource.setStatus(HttpStatus.NOT_FOUND);
		noResource.addHeader(HttpHeaderName.ContentType, ContentType.Txt);
		return noResource;
	}

	private Object findResourceAndSetUrlTemplate(Request request) {
		String url = request.getUrl();
		if (noVariableResourceMap.containsKey(url)) {
			request.setUrlTemplate(url);
			logger.debug("found \"{}\" in no variable resources.",url);
			return noVariableResourceMap.get(url);
		} else {
			Set<String> urls = withVariableResourceMap.keySet();
			for (String _url : urls) {
				UriTemplate uriTemplate = uriTemplateMap.get(_url);
				if (uriTemplate.matches(url)) {
					request.setUrlTemplate(_url);
					logger.debug("found templete \"{}\" by url \"{}\"", _url, url);
					return withVariableResourceMap.get(_url);
				}
			}
		}
		return null;
	}

	private void cacheResourceOperation(String url, Object resource) {
		for (HttpVerb verb : HttpVerb.values()) {
			Method m = AnnotationUtil.getHttpVerbMethod(resource.getClass(),
					verb);
			if (m == null) {
				continue;
			}
			ResourceMethodMeta methodMeta = buildResourceMethodMeta(m, resource);
			if (m != null) {
				String key = getCacheKey(url, verb);
				resOperateMap.put(key, methodMeta);
				logger.debug("cache resource operation method : {} - {}",
						new Object[] { key, m });
			}
		}
	}

	private ResourceMethodMeta buildResourceMethodMeta(Method m, Object resource) {
		ResourceMethodMeta methodMeta = new ResourceMethodMeta();
		methodMeta.setResource(resource);
		methodMeta.setMethod(m);
		// accepts
		methodMeta.setAcceptAnnotationData(AnnotationUtil
				.getMethodAcceptAnnotationData(m));
		return methodMeta;
	}

	private String getCacheKey(String url, HttpVerb verb) {
		return verb + " " + url;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		logger.debug("I'm aware of applicationContext:{}", applicationContext);
	}

	/**
	 * Http Verb Method invoker interface
	 * 
	 * @author bigknife
	 * 
	 */
	public static interface IHttpVerbInvoker {
		/**
		 * 调用资源方法
		 * 
		 * @param rmm
		 * @return
		 */
		Object invoke(Request restRequest, ResourceMethodMeta rmm)
				throws Throwable;
	}

	public static interface UriTemplateMapAware {
		/**
		 * 设置uriTemplate map
		 * 
		 * @param uriTemplateMap
		 */
		void setUrlTemplateMap(Map<String, UriTemplate> uriTemplateMap);
	}

}
