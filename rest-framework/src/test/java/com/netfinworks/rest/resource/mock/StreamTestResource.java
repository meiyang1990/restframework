/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.netfinworks.rest.annotation.Accept;
import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.BodyAs;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.JsonRender;
import com.netfinworks.rest.util.ContentType;

/**
 * @author bigknife
 *
 */
@WebResource(url="/stream")
public class StreamTestResource {
	@Verb(HttpVerb.POST)
	@Accept(contentType=ContentType.FormUrlEncoded)
	@Render(render=JsonRender.class)
	public String streamAsText(@Body(as=BodyAs.Stream) InputStream in) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(in, baos);
		byte []  ba = baos.toByteArray();
		return new String(ba);
	}
}
