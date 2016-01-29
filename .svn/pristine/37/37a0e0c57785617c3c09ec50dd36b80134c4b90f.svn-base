/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;

/**
 * @author bigknife
 *
 */
public class XmlRenderTest {

	@Test
	public void testRender() throws IOException{
		XmlRender render = new XmlRender();
		Request request = new Request();
		request.setUrlTemplate("/order/{id}");
		Map<String, Object> data = new HashMap<String, Object>();
		
		Response response = render.render(data, request);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copyLarge(response.getInputStream(), baos);
		String s = baos.toString();
		Assert.assertEquals(s,"<map/>");
		
		render.setEncoding("error");
		response = render.render(data, request);
		Assert.assertNull(response);
		
	}
}
