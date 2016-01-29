/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;

/**
 * 异常渲染类
 * @author bigknife
 *
 */
public class BaseExceptionRender implements IRender {
	private Map<String, IRender> exceptionRenders = new HashMap<String, IRender>();
	
	/**
	 * @param exceptionRenders the exceptionRenders to set
	 */
	public void setExceptionRenders(Map<String, IRender> exceptionRenders) {
		this.exceptionRenders = exceptionRenders;
	}

	@Override
	public Response render(Object resultObject, Request request) {
		throw new IllegalAccessError("正常业务不可调用异常渲染类渲染");
	}

	@Override
	public Response renderException(Throwable exception, Request request) {
		IRender render = exceptionRenders.get(exception.getClass().getName());
		if(render != null){
			return render.renderException(exception, request);
		}
		Response resp = new Response();
		resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		resp.setInputStream(new ByteArrayInputStream("SERVER INTERNAL ERROR".getBytes()));
		return resp;
	}

}
