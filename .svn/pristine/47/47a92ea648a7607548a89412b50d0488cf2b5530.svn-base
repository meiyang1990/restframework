/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.annotation.HeaderParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/headerparam")
public class HeaderParamResource {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Verb(HttpVerb.GET)
	@Render(render=JsonRender.class)
	public String hello(@HeaderParam(name="content-type") String contentType){
		logger.info("content-type is {}",contentType);
		return "hello,world" + contentType;
	}
}
