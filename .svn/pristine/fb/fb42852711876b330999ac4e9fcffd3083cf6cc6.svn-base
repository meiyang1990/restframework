/**
 * 
 */
package com.netfinworks.rest.auth;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.netfinworks.rest.util.HttpHeaderName;

/**
 * @author bigknife
 * 
 */
public class BaseAuthCheckTest {
	@Test
	public void testCheck() {
		BasicAuthCheck check = new BasicAuthCheck();
		Map<String, String> headers = new HashMap<String, String>();
		String userpwd = Base64
				.encodeBase64String("bigknife:123456".getBytes());
		System.out.println(userpwd);
		headers.put(HttpHeaderName.Authorization, "Basic " + userpwd);
		boolean passed = check.checkAuth(headers).isPassed();
		Assert.assertEquals(passed, true);

		userpwd = Base64.encodeBase64String("song:123456".getBytes());
		headers.put(HttpHeaderName.Authorization, "Basic " + userpwd);
		passed = check.checkAuth(headers).isPassed();
		Assert.assertEquals(passed, false);
	}
}
