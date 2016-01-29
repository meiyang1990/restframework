/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import com.thoughtworks.xstream.XStream;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;

/**
 * @author bigknife
 *
 */
public class XmlRender implements IRender {
	private String encoding = Encoding.UTF_8;
	private XStream xstream = new XStream();
	@Override
	public Response render(Object resultObject, Request request) {
		String obj = xstream.toXML(resultObject);
		Response resp = new Response();
		try {
			resp.setInputStream(new ByteArrayInputStream(obj.getBytes(encoding)));
			resp.addHeader(HttpHeaderName.ContentType, ContentType.Xml);
			return resp;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			//never mind
		}
		return null;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	@Override
	public Response renderException(Throwable exception, Request request) {
		throw new IllegalAccessError("un implemented method!");
	}
}
