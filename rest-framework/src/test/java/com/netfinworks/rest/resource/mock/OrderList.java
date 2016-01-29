package com.netfinworks.rest.resource.mock;

import com.netfinworks.rest.annotation.Authorized;
import com.netfinworks.rest.annotation.QueryString;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.FtlRender;

@WebResource(url="/order/list")
@Render(render=FtlRender.class)
@Authorized
public class OrderList {
	@Verb(HttpVerb.GET)
	public Order initPage(@QueryString Order order){
		return order == null ? new Order("1", "初始订单", 250000) : order;
	}
}
