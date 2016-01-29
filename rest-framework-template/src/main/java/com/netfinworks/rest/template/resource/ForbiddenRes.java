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
 * 禁止访问资源
 * @author bigknife
 *
 */
@WebResource(url="/forbidden")
@Render(renderRef="portalRender")
@Component
public class ForbiddenRes {
	@Verb(HttpVerb.GET)
	public CommonPage<Object> forbiddenPage(){
		CommonPage<Object> page = new CommonPage<Object>();
		page.setTitle("您访问的资源不允许访问！");
		return page;
	}
}
