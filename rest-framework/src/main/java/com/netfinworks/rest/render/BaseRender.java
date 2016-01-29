/**
 * 
 */
package com.netfinworks.rest.render;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;

/**
 * IRender接口基本实现
 * @author bigknife
 *
 */
public class BaseRender implements IRender {

	@Override
	public Response render(Object resultObject, Request request) {
		throw new IllegalAccessError();
	}

	@Override
	public Response renderException(Throwable exception, Request request) {
		throw new IllegalAccessError();
	}

}
