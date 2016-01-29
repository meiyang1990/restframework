/**
 * 
 */
package com.netfinworks.rest.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author bigknife
 *
 */
public class RequestTest {
	private Request request;
	@Before
	public void init(){
		request = new Request();
	}
	
	@Test
	public void testGetFirstQueryParameter(){
		String value = request.getFirstQueryParameter("hello");
		Assert.assertNull(value);
		
		request.addQueryParameters("hello", new String[]{"world","fuck"});
		value = request.getFirstQueryParameter("hello");
		Assert.assertNotNull(value);
		Assert.assertEquals("world", value);
		Assert.assertNotSame("fuck", value);
	}
}
