/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.exception.ResourceException;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/resourceexception")
@Render(render=JsonRender.class)
public class ResourceExceptionResource {

	
	@Verb(HttpVerb.GET)
	public void exception() {
		ResourceException e = new ResourceException("test reource exception");
		e.setHttpStatus(HttpStatus.BAD_REQUEST);
		throw e;
	}

}
