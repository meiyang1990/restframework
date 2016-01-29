/**
 * 
 */
package com.netfinworks.rest.exception;

import java.io.ByteArrayInputStream;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.Response;

/**
 * @author bigknife
 *
 */
public class ResourceException extends RuntimeException {
	private static final long serialVersionUID = 1906986777156754106L;
	private Response exeptionResponse = new Response();

	private void init(String msg){
		//默认状态码为500
		exeptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		if(msg != null && msg.length() > 0){
			exeptionResponse.setInputStream(new ByteArrayInputStream(msg.getBytes()));
		}
	}
	
	/**
	 * 设置自定义状态码
	 * @param status
	 */
	public void setHttpStatus(HttpStatus status){
		exeptionResponse.setStatus(status);
	}
	
	/**
	 * 获取异常响应
	 * @return
	 */
	public Response getExceptionResponse(){
		return this.exeptionResponse;
	}
	/**
	 * 
	 */
	public ResourceException() {
		init(null);
	}

	/**
	 * @param message
	 */
	public ResourceException(String message) {
		super(message);
		init(message);
		
	}

	/**
	 * @param cause
	 */
	public ResourceException(Throwable cause) {
		super(cause);
		init(cause.getMessage());
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ResourceException(String message, Throwable cause) {
		super(message, cause);
		init(message);
	}

}
