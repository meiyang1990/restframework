/**
 * 
 */
package com.netfinworks.rest.guardian;

import org.springframework.stereotype.Component;

import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.restx.resp.CommonPage;

/**
 * 禁止访问资源提示页面
 * @author bigknife
 */
@Component
@WebResource(url="/forbidden")
@Render(renderRef="portalRender")
public class ForbiddenRes {
	
	@Verb(HttpVerb.GET)
	public CommonPage<Object> forbiddenPage(){
		CommonPage<Object> page = new CommonPage<Object>();
		page.setTitle("您无权访问统一日志管理系统！");
		page.addExtData("activeNav", "unilog");
		return page;
	}
}
