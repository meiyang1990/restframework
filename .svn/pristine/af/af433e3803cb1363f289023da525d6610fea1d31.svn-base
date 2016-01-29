/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.annotation.Accept;
import com.netfinworks.rest.annotation.Body;
import com.netfinworks.rest.annotation.QueryParam;
import com.netfinworks.rest.annotation.QueryString;
import com.netfinworks.rest.annotation.Render;
import com.netfinworks.rest.annotation.UrlParam;
import com.netfinworks.rest.annotation.Verb;
import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.render.XmlRender;
import com.netfinworks.rest.util.ContentType;

/**
 * @author bigknife
 *
 */
@WebResource(url="/order/{id}")
@Render
public class OrderResource {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Verb(HttpVerb.GET)
	@Accept(contentType=ContentType.FormUrlEncoded)
	public Order getOrder(@UrlParam(name="id") String id,
				@QueryParam(name="version") Integer version, @QueryString Order order){
		logger.info("arg id = {}, version = {}, maybeNull = {}",new Object[]{id,version,order});
		return new Order(id, "test order, 中文支持测试", 1000);
	}
	
	@Verb(HttpVerb.POST)
	@Accept(contentType=ContentType.FormUrlEncoded)
	@Render(render=XmlRender.class)
	public Order createOrder(@Body Order order){
		logger.info("order : id={},name={},amount={}",new Object[]{order.getId(),order.getName(),order.getAmount()});
		return order;
	}
	
	@Verb(HttpVerb.PUT)
	@Accept(contentType=ContentType.FormUrlEncoded)
	@Render(render=XmlRender.class)
	public void modifyOrder(@Body Order order){
		logger.info("order : id={},name={},amount={}",new Object[]{order.getId(),order.getName(),order.getAmount()});
		throw new RuntimeException("this is a exception test!");
	}
}
