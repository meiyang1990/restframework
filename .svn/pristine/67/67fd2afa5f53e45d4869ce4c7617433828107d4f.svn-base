/**
 * 
 */
package com.netfinworks.rest.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.netfinworks.rest.annotation.AcceptAnnotationData;
import com.netfinworks.rest.annotation.BodyAnnotationData;
import com.netfinworks.rest.annotation.CookieParamAnnotationData;
import com.netfinworks.rest.annotation.HeaderParamAnnotationData;
import com.netfinworks.rest.annotation.QueryParamAnnotationData;
import com.netfinworks.rest.annotation.QueryStringAnnotationData;
import com.netfinworks.rest.annotation.UrlParamAnnotationData;
import com.netfinworks.rest.convert.IParamConvert;
import com.netfinworks.rest.convert.MultiPartFormDataParamConvert;
import com.netfinworks.rest.convert.NeverUsedParamConvert;
import com.netfinworks.rest.enums.BodyAs;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.server.DefaultRestServer.IHttpVerbInvoker;
import com.netfinworks.rest.server.DefaultRestServer.UriTemplateMapAware;
import com.netfinworks.rest.util.AnnotationUtil;
import com.netfinworks.rest.util.ConvertUtil;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.Magic;
import com.netfinworks.rest.util.UriTemplate;

/**
 * @author bigknife
 * 
 */
public class DefaultHttpVerbInvoker implements IHttpVerbInvoker, ApplicationContextAware, UriTemplateMapAware {
	private ApplicationContext applicationContext;
	private Map<String, UriTemplate> urlTemplateMap;

	@Override
	public Object invoke(Request restRequest, ResourceMethodMeta rmm) throws Throwable {
		Method m = rmm.getMethod();
		Class<?>[] parameterTypes = rmm.getMethod().getParameterTypes();
		return (parameterTypes == null || parameterTypes.length == 0) ?
		// 无参数方法
		m.invoke(rmm.getResource())
				:
				// 有参数方法
				invokeWithParams(restRequest, rmm);
	}

	private Object invokeWithParams(Request restRequest, ResourceMethodMeta rmm) throws Throwable {
		Class<?>[] parameterTypes = rmm.getMethod().getParameterTypes();
		Object[] args = new Object[parameterTypes.length];

		Method m = rmm.getMethod();
		for (int i = 0; i < parameterTypes.length; i++) {
			UrlParamAnnotationData urlParamData = AnnotationUtil.getUrlParamAnnotationData(m, i);
			// 指定了urlparam参数,则优先使用urlparam
			if (urlParamData != null) {
				UriTemplate uriTemplate = urlTemplateMap.get(restRequest.getUrlTemplate());
				Map<String, String> uriParams = uriTemplate.match(restRequest.getUrl());
				String rawValue = uriParams.get(urlParamData.getName());
				args[i] = rawValue == null ? null : convertUrlParam(rawValue, urlParamData, parameterTypes[i]);
				// 下一个参数
				continue;
			}

			// 指定了查询参数，查询参数必须符合url form encoded 规范
			QueryParamAnnotationData queryParamData = AnnotationUtil.getQueryParamAnnotationData(m, i);
			Map<String, String[]> queryParameters = restRequest.getQueryParameters();
			if (queryParamData != null && queryParameters != null) {

				String[] rawValues = queryParameters.get(queryParamData.getName());
				args[i] = (rawValues == null) ? null : convertQueryParam(rawValues, queryParamData, parameterTypes[i]);
				// 下一个参数
				continue;
			}

			// 指定了查询字符串，将整个查询字符串转换为一个参数对象
			QueryStringAnnotationData queryStringData = AnnotationUtil.getQueryStringAnnotationData(m, i);
			if (queryStringData != null) {
				String queryString = restRequest.getQueryString();
				if (queryString == null || queryString.trim().length() == 0) {
					args[i] = null;
				} else {
					queryString = Encoding.decodeUrlEncodedString(queryString, queryStringData.getEncoding());
					IParamConvert convert = createParamConvertForQueryStringAndBody(rmm, queryStringData.getConverter());
					args[i] = convert.convert(queryString, parameterTypes[i]);
				}
				continue;
			}

			//指定了HeaderParam
			HeaderParamAnnotationData headerParamAnnotationData = AnnotationUtil.getHeaderParamAnnotationData(m, i);
			if (headerParamAnnotationData != null) {
				String headerName = headerParamAnnotationData.getName();
				Map<String, String> headers = restRequest.getHeaders();
				String rawValue = headers == null ? null : headers.get(headerName);

				String convertRef = headerParamAnnotationData.getConverterRef();
				IParamConvert convert = Magic.EmtpyString.equals(convertRef) ? headerParamAnnotationData.getConverter().newInstance()
						: applicationContext.getBean(convertRef, IParamConvert.class);
				args[i] = convert.convert(rawValue, parameterTypes[i]);
				continue;
			}

			//指定了CookieParam
			CookieParamAnnotationData cookieParamAnnotationData = AnnotationUtil.getCookieParamAnnotationData(m, i);
			if (cookieParamAnnotationData != null) {
				String cookieName = cookieParamAnnotationData.getName();
				
				String rawValue = restRequest.getCookie(cookieName);

				String convertRef = cookieParamAnnotationData.getConverterRef();
				IParamConvert convert = Magic.EmtpyString.equals(convertRef) ? cookieParamAnnotationData.getConverter().newInstance()
						: applicationContext.getBean(convertRef, IParamConvert.class);
				args[i] = convert.convert(rawValue, parameterTypes[i]);
				continue;
			}

			// 指定了body，则将整个body作为一个对象处理，根据Body的as属性，确定为字符串或者流来处理
			BodyAnnotationData bodyData = AnnotationUtil.getBodyAnnotationData(m, i);
			if (bodyData != null) {
				if (BodyAs.String.equals(bodyData.getAs())) {
					// 处理为字符串
					String bodyString = streamAsString(restRequest.getInputStream(), bodyData.getEncoding());
					IParamConvert convert = Magic.EmtpyString.equals(bodyData.getConverterRef()) ? null : applicationContext.getBean(
							bodyData.getConverterRef(), IParamConvert.class);
					convert = (convert == null) ? createParamConvertForQueryStringAndBody(rmm, bodyData.getConverter()) : convert;
					args[i] = convert.convert(bodyString, parameterTypes[i]);
				} else if (BodyAs.MultiPartFormData.equals(bodyData.getAs())) {
					//multipartform处理，处理上传文件
					boolean isMultipart = ServletFileUpload.isMultipartContent(restRequest.getRawRequest());
					if (isMultipart) {
						IParamConvert convert = Magic.EmtpyString.equals(bodyData.getConverterRef()) ? null : applicationContext.getBean(
								bodyData.getConverterRef(), IParamConvert.class);
						convert = (convert == null) ? createParamConvertForQueryStringAndBody(rmm, bodyData.getConverter()) : convert;
						if (convert instanceof MultiPartFormDataParamConvert) {
							//作为multipart处理
							args[i] = ((MultiPartFormDataParamConvert) convert).convert(restRequest, parameterTypes[i]);
						} else {
							//处理为流
							args[i] = restRequest.getInputStream();
						}
					}
				} else {
					// 处理为流
					args[i] = restRequest.getInputStream();
				}

				continue;
			}

			// 如果没有指定注解，只能传入null
			args[i] = null;
		}
		return m.invoke(rmm.getResource(), args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

	}

	@Override
	public void setUrlTemplateMap(Map<String, UriTemplate> urlTemplateMap) {
		this.urlTemplateMap = urlTemplateMap;
	}

	private static String streamAsString(InputStream in, String encoding) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(in, baos);
		return new String(baos.toByteArray(), encoding);
	}

	/**
	 * 转换UrlParam参数
	 * 
	 * @param rawValue
	 * @param urlParamData
	 * @param type
	 * @return
	 * @throws Throwable
	 */
	private Object convertUrlParam(String rawValue, UrlParamAnnotationData urlParamData, Class<?> type) throws Throwable {
		String _rawValue = Encoding.decodeUrlEncodedString(rawValue, urlParamData.getEncoding());
		String converterRef = urlParamData.getConverterRef();
		Class<? extends IParamConvert> converter = urlParamData.getConverter();
		IParamConvert convertInstance = Magic.EmtpyString.equals(converterRef) ? converter.newInstance()
				: (IParamConvert) applicationContext.getBean(converterRef);
		return convertInstance.convert(_rawValue, type);
	}

	private Object convertQueryParam(String[] rawValues, QueryParamAnnotationData queryParamData, Class<?> type) throws Throwable {
		String converterRef = queryParamData.getConverterRef();
		Class<? extends IParamConvert> converter = queryParamData.getConverter();
		IParamConvert convertInstance = Magic.EmtpyString.equals(converterRef) ? converter.newInstance()
				: (IParamConvert) applicationContext.getBean(converterRef);

		return ConvertUtil.convertUrlEncodedStringAsArray(rawValues, queryParamData.getEncoding(), type, convertInstance);

	}

	private static IParamConvert createParamConvertForQueryStringAndBody(ResourceMethodMeta rmm,
			Class<? extends IParamConvert> secondSelection) throws InstantiationException, IllegalAccessException {
		AcceptAnnotationData aad = rmm.getAcceptAnnotationData();
		Class<? extends IParamConvert> firstSelection = aad == null ? null : aad.getParamConvert();

		return firstSelection == null || firstSelection.equals(NeverUsedParamConvert.class) ? secondSelection.newInstance()
				: firstSelection.newInstance();
	}
}
