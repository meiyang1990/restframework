/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import java.util.HashMap;
import java.util.Map;

import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.JsonRender;

/**
 * @author bigknife
 *
 */
@WebResource(url="/string/array")
@Render(render=JsonRender.class)
public class StringArrayParamTestResource {

	@Verb(HttpVerb.GET)
	public Map<String, Object> testArray(@QueryParam(name="idList") int [] idList){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("test", idList);
		return ret;
	}
}
