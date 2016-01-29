/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;

import org.codehaus.jackson.map.ObjectMapper;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;

/**
 * @author bigknife
 *
 */
public class DefaultRender implements IRender {
	private static ObjectMapper om = new ObjectMapper();
	@Override
	public Response render(Object resultObject, Request request) {
		//TODO: 暂时用json，需改为根据请求的内容协商机制，自适应IRender
		try {
			byte [] data =  om.writeValueAsBytes(resultObject);
			Response resp = new Response();
			resp.setInputStream(new ByteArrayInputStream(data));
			resp.setStatus(HttpStatus.OK);
			resp.addHeader("Content-Type", "application/json;charset=utf-8");
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
