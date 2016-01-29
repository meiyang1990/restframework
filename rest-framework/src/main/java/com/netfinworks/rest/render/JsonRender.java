/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.HttpHeaderName;

/**
 * @author bigknife
 *
 */
public class JsonRender implements IRender {
	private static ObjectMapper om = new ObjectMapper();
	@Override
	public Response render(Object resultObject, Request request) {
		try {
			Response resp = new Response();
			if (resultObject != null) {
				byte[] data = om.writeValueAsBytes(resultObject);
				resp.setInputStream(new ByteArrayInputStream(data));
			}
			resp.setStatus(HttpStatus.OK);
			resp.addHeader(HttpHeaderName.ContentType, ContentType.Json);
			
			return resp;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Response renderException(Throwable exception, Request request) {
		throw new IllegalAccessError("un implemented method!");
	}
}
