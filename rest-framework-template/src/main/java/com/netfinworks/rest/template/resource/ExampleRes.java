/**
 * 
 */
package com.netfinworks.rest.template.resource;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.restx.resp.CommonPage;

/**
 * @author bigknife
 *
 */
@WebResource(url="/example")
@Render(renderRef="appRender")
@Component
public class ExampleRes {
	
	@Verb(HttpVerb.GET)
	public CommonPage<Object> examplgePage(){
		CommonPage<Object> page = new CommonPage<Object>();
		page.setTitle("netfinworks - inf - restframework - template - example");
		return page;
	}
}
