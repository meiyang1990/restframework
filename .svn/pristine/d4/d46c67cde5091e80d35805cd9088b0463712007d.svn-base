/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import java.io.ByteArrayInputStream;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.render.IRender;

/**
 * @author bigknife
 *
 */
public class RenderMock implements IRender {

	@Override
	public Response render(Object resultObject, Request request) {
		Response response = new Response();
		response.setInputStream(new ByteArrayInputStream("CONSTANT".getBytes()));
		return response;
	}

	@Override
	public Response renderException(Throwable exception, Request request) {
		return null;
	}

}
