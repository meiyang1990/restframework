/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import com.netfinworks.rest.annotation.Authorized;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/auth/resource")
@Authorized
@Render(render=JsonRender.class)
public class AuthResource {

	@Verb
	public String hello(){
		return "This is secret";
	}
}
