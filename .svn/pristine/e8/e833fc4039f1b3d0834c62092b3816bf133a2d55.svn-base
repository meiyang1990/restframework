/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/noparam")
@Render(render=JsonRender.class)
public class NoParamResource {
	
	@Verb
	public String hello(){
		return "hello,world";
	}
}
