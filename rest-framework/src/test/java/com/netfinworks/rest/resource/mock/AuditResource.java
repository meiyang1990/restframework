/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import com.netfinworks.rest.annotation.Audit;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/audit")
@Audit
@Render(render=JsonRender.class)
public class AuditResource {
	@Verb(HttpVerb.GET)
	public String hello(){
		return "hello,world";
	}
	@Verb(HttpVerb.POST)
	@Audit(auditRef="crazyAudit")
	public String post(){
		return "hello,world";
	}
}
