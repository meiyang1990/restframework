/**
 * 
 */
package com.netfinworks.rest.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

import com.netfinworks.rest.annotation.Accept;
import com.netfinworks.rest.annotation.AcceptAnnotationData;
import com.netfinworks.rest.annotation.Audit;
import com.netfinworks.rest.annotation.AuditAnnotationData;
import com.netfinworks.rest.annotation.AuthorizeAnnotationData;
import com.netfinworks.rest.annotation.Authorized;
import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.BodyAnnotationData;
import com.netfinworks.rest.annotation.CookieParam;
import com.netfinworks.rest.annotation.CookieParamAnnotationData;
import com.netfinworks.rest.annotation.HeaderParam;
import com.netfinworks.rest.annotation.HeaderParamAnnotationData;
import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.QueryParamAnnotationData;
import com.netfinworks.rest.annotation.QueryString;
import com.netfinworks.rest.annotation.QueryStringAnnotationData;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.RenderAnnotationData;
import com.netfinworks.rest.annotation.UrlParam;
import com.netfinworks.rest.annotation.UrlParamAnnotationData;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.annotation.WebResourceAnnotationData;
import com.netfinworks.rest.audit.IResourceAudit;
import com.netfinworks.rest.auth.IAuthCheck;
import com.netfinworks.rest.convert.IParamConvert;
import com.netfinworks.rest.enums.BodyAs;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.IRender;
import com.netfinworks.rest.server.ResourceMethodMeta;

/**
 * @author bigknife
 * 
 */
public final class AnnotationUtil {

	public static WebResourceAnnotationData getWebResourceAnnotationData(Object bean) {
		if (bean != null) {
			Annotation anno = AnnotationUtils.getAnnotation(bean.getClass(), WebResource.class);
			if (anno != null) {
				WebResourceAnnotationData wrad = new WebResourceAnnotationData();
				wrad.setUrl((String) AnnotationUtils.getValue(anno, Magic.Url));
				wrad.setUrlMatchKind((UrlMatchKind) AnnotationUtils.getValue(anno, Magic.MatchKind));
				return wrad;
			}
		}
		return null;
	}

	public static Method getHttpVerbMethod(Class<?> cls, HttpVerb verb) {
		Method ret = null;
		if (cls != null) {
			Method[] declaredMethods = cls.getDeclaredMethods();
			for (Method method : declaredMethods) {
				Annotation anno = AnnotationUtils.findAnnotation(method, Verb.class);
				if (anno != null) {
					HttpVerb theVerb = (HttpVerb) AnnotationUtils.getValue(anno);
					if (theVerb.equals(verb)) {
						ret = method;
						break;
					}
				}
			}
			if (ret == null) {
				ret = getHttpVerbMethod(cls.getSuperclass(), verb);
			}
		}
		return ret;
	}

	/**
	 * 从方法的第<code>i<code>个参数重获取UrlParam信息
	 * 
	 * @see {@link UrlParam}
	 * @see {@link UrlParamAnnotationData}
	 * 
	 * @param m 方法
	 * @param i 参数索引，从0开始
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static UrlParamAnnotationData getUrlParamAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;
		UrlParamAnnotationData data = urlParamAnnotationData.get(key);
		if (data == null) {
			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof UrlParam) {
					data = new UrlParamAnnotationData();
					data.setName((String) AnnotationUtils.getValue(anno, Magic.Name));
					data.setConverter((Class<IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					data.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));
					data.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));

					urlParamAnnotationData.put(key, data);
				}
			}
		}
		return data;
	}

	private static Map<String, UrlParamAnnotationData> urlParamAnnotationData = new HashMap<String, UrlParamAnnotationData>();

	/**
	 * 从方法的第<code>i<code>个参数重获取QueryParam信息
	 * 
	 * @see {@link QueryParam}
	 * @see {@link QueryParamAnnotationData}
	 * @param m 方法
	 * @param i 参数索引，从0开始
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static QueryParamAnnotationData getQueryParamAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;
		QueryParamAnnotationData data = cacheQueryParamAnnotationData.get(key);
		if (data == null) {
			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof QueryParam) {
					data = new QueryParamAnnotationData();
					data.setName((String) AnnotationUtils.getValue(anno, Magic.Name));
					data.setConverter((Class<IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					data.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));
					data.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));
					cacheQueryParamAnnotationData.put(key, data);
				}
			}
		}
		return data;
	}

	private static Map<String, QueryParamAnnotationData> cacheQueryParamAnnotationData = new HashMap<String, QueryParamAnnotationData>();

	@SuppressWarnings("unchecked")
	public static QueryStringAnnotationData getQueryStringAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;

		QueryStringAnnotationData data = cacheQueryStringAnnotationData.get(key);

		if (data == null) {

			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof QueryString) {
					data = new QueryStringAnnotationData();
					data.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));
					data.setConverter((Class<? extends IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					data.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));

					cacheQueryStringAnnotationData.put(key, data);
				}
			}
		}
		return data;
	}

	private static Map<String, QueryStringAnnotationData> cacheQueryStringAnnotationData = new HashMap<String, QueryStringAnnotationData>();

	@SuppressWarnings("unchecked")
	public static AcceptAnnotationData getMethodAcceptAnnotationData(Method m) {
		Annotation[] annotations = m.getDeclaredAnnotations();
		for (Annotation anno : annotations) {
			if (anno instanceof Accept) {
				AcceptAnnotationData aad = new AcceptAnnotationData();

				aad.setContentType((String) AnnotationUtils.getValue(anno, Magic.ContentType));
				aad.setParamConvert((Class<? extends IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
				return aad;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static BodyAnnotationData getBodyAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;
		BodyAnnotationData bad = cacheBodyAnnotationData.get(key);
		if (bad == null) {
			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof Body) {
					bad = new BodyAnnotationData();
					bad.setAs((BodyAs) AnnotationUtils.getValue(anno, Magic.As));
					bad.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));
					bad.setConverter((Class<? extends IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					bad.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));
					cacheBodyAnnotationData.put(key, bad);
				}
			}
		}
		return bad;
	}

	private static Map<String, BodyAnnotationData> cacheBodyAnnotationData = new HashMap<String, BodyAnnotationData>();

	/**
	 * 获取RenderAnnotationData
	 * 
	 * @param rmm
	 * @return
	 */
	public static RenderAnnotationData getWebResourceRenderAnnotationData(ResourceMethodMeta rmm) {
		String key = rmm.getMethod().toGenericString() + rmm.getResource().getClass() + rmm.getResource().hashCode();
		RenderAnnotationData rad = renderAnnotationDataMap.get(key);
		if (rad == null) {
			// 优先使用方法级别的
			Annotation annoMethod = AnnotationUtils.getAnnotation(rmm.getMethod(), Render.class);
			if (annoMethod != null) {
				rad = buildRenderAnnotationData(annoMethod);
				renderAnnotationDataMap.put(key, rad);
				return rad;
			}

			// 如果没有，则用对象级别的
			Annotation annoObj = AnnotationUtils.getAnnotation(rmm.getResource().getClass(), Render.class);
			if (annoObj != null) {
				rad = buildRenderAnnotationData(annoObj);
				renderAnnotationDataMap.put(key, rad);
				return rad;
			}
		}
		return rad;
	}

	private static Map<String, RenderAnnotationData> renderAnnotationDataMap = new HashMap<String, RenderAnnotationData>();

	/**
	 * 获取AuthorizeAnnotationData 数据，缓存以减少反射调用，提高效率
	 * 
	 * @param rmm
	 * @return
	 */
	public static AuthorizeAnnotationData getAuthorizedAnnotationData(ResourceMethodMeta rmm) {
		String key = rmm.getMethod().toGenericString() + rmm.getResource().getClass() + rmm.getResource().hashCode();
		AuthorizeAnnotationData aad = authorizeAnnotationDataCache.get(key);
		if (aad == null) {
			// 优先使用方法级别的
			Annotation annoMethod = AnnotationUtils.getAnnotation(rmm.getMethod(), Authorized.class);
			if (annoMethod != null) {
				aad = buildAuthorizeAnnotationData(annoMethod);
			} else {
				// 如果没有，则用对象级别的
				Annotation annoObj = AnnotationUtils.getAnnotation(rmm.getResource().getClass(), Authorized.class);
				if (annoObj != null) {
					aad = buildAuthorizeAnnotationData(annoObj);
				}
			}
			authorizeAnnotationDataCache.put(key, aad);
		}

		return aad;
	}

	private static Map<String, AuthorizeAnnotationData> authorizeAnnotationDataCache = new HashMap<String, AuthorizeAnnotationData>();

	/**
	 * 资源的访问审计元数据
	 * 
	 * @param rmm
	 * @return
	 */
	public static AuditAnnotationData getAuditAnnotationData(ResourceMethodMeta rmm) {
		String key = rmm.getMethod().toGenericString() + rmm.getResource().getClass() + rmm.getResource().hashCode();
		AuditAnnotationData aad = auditAnnotationDataCache.get(key);
		if (aad == null) {
			Annotation annoMethod = AnnotationUtils.getAnnotation(rmm.getMethod(), Audit.class);
			if (annoMethod != null) {
				aad = buildAuditAnnotationData(annoMethod);
			} else {
				// 如果没有，则用对象级别
				Annotation annoObj = AnnotationUtils.getAnnotation(rmm.getResource().getClass(), Audit.class);
				if (annoObj != null) {
					aad = buildAuditAnnotationData(annoObj);
				}
			}
			auditAnnotationDataCache.put(key, aad);
		}
		return aad;
	}

	private static Map<String, AuditAnnotationData> auditAnnotationDataCache = new HashMap<String, AuditAnnotationData>();

	/**
	 * 从方法的第<code>i<code>个参数重获取QueryParam信息
	 * 
	 * @see {@link QueryParam}
	 * @see {@link QueryParamAnnotationData}
	 * @param m 方法
	 * @param i 参数索引，从0开始
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HeaderParamAnnotationData getHeaderParamAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;
		HeaderParamAnnotationData data = cacheHeaderParamAnnotationData.get(key);
		if (data == null) {
			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof HeaderParam) {
					data = new HeaderParamAnnotationData();
					data.setName((String) AnnotationUtils.getValue(anno, Magic.Name));
					data.setConverter((Class<IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					data.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));
					data.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));
					cacheHeaderParamAnnotationData.put(key, data);
				}
			}
		}
		return data;
	}

	private static Map<String, HeaderParamAnnotationData> cacheHeaderParamAnnotationData = new HashMap<String, HeaderParamAnnotationData>();

	@SuppressWarnings("unchecked")
	public static CookieParamAnnotationData getCookieParamAnnotationData(Method m, int i) {
		String key = m.toGenericString() + i;
		CookieParamAnnotationData data = cacheCookiewParamAnnotationData.get(key);
		if (data == null) {
			Annotation[] annotations = m.getParameterAnnotations()[i];
			for (Annotation anno : annotations) {
				if (anno instanceof CookieParam) {
					data = new CookieParamAnnotationData();
					data.setName((String) AnnotationUtils.getValue(anno, Magic.Name));
					data.setConverter((Class<IParamConvert>) AnnotationUtils.getValue(anno, Magic.Converter));
					data.setConverterRef((String) AnnotationUtils.getValue(anno, Magic.ConverterRef));
					data.setEncoding((String) AnnotationUtils.getValue(anno, Magic.Encoding));
					cacheCookiewParamAnnotationData.put(key, data);
				}
			}
		}
		return data;
	}

	private static Map<String, CookieParamAnnotationData> cacheCookiewParamAnnotationData = new HashMap<String, CookieParamAnnotationData>();

	@SuppressWarnings("unchecked")
	private static AuditAnnotationData buildAuditAnnotationData(Annotation anno) {
		AuditAnnotationData aad = new AuditAnnotationData();
		aad.setAuditRef((String) AnnotationUtils.getValue(anno, Magic.AuditRef));
		aad.setAudit((Class<? extends IResourceAudit>) AnnotationUtils.getValue(anno, Magic.Audit));
		return aad;
	}

	@SuppressWarnings("unchecked")
	private static AuthorizeAnnotationData buildAuthorizeAnnotationData(Annotation anno) {
		AuthorizeAnnotationData aad = new AuthorizeAnnotationData();
		aad.setCheck((Class<? extends IAuthCheck>) AnnotationUtils.getValue(anno, Magic.Check));
		aad.setCheckRef((String) AnnotationUtils.getValue(anno, Magic.CheckRef));
		return aad;
	}

	@SuppressWarnings("unchecked")
	private static RenderAnnotationData buildRenderAnnotationData(Annotation anno) {
		RenderAnnotationData rad = new RenderAnnotationData();
		rad.setRender((Class<? extends IRender>) AnnotationUtils.getValue(anno, Magic.Render));
		rad.setRenderRef((String) AnnotationUtils.getValue(anno, Magic.RenderRef));

		rad.setExceptionRender((Class<? extends IRender>) AnnotationUtils.getValue(anno, Magic.ExceptionRender));
		rad.setExceptionRenderRef((String) AnnotationUtils.getValue(anno, Magic.ExceptionRenderRef));

		return rad;
	}
}
