/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.HttpHeaderName;

/**
 * @author knico
 * 
 */
public class TxtRender implements IRender {

	@Override
	public Response render(Object resultObject, Request request) {
		try {
			Response resp = new Response();
			resp.setInputStream(new ByteArrayInputStream(("" + resultObject)
					.getBytes("UTF-8")));
			resp.setStatus(HttpStatus.OK);
			resp.addHeader(HttpHeaderName.ContentType, ContentType.Txt);

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
