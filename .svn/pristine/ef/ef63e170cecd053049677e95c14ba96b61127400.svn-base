/**
 * 
 */
package com.netfinworks.rest.render;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;

/**
 * 将资源渲染为合适的表述(以Response表示)
 * @author bigknife
 *
 */
public interface IRender {
	/**
	 * 将资源处理结果对象渲染为合适的response
	 * @param resultObject 资原处理结果
	 * @param request 请求对象
	 * @return 合适的响应对象 ，如果出现异常，需抛出合适的RuntimeException
	 */
	Response render(Object resultObject, Request request);
	
	/**
	 * 渲染请求异常信息为合适的response
	 * @param exception 异常信息
	 * @param request 请求
	 * @return
	 */
	Response renderException(Throwable exception, Request request);
}
