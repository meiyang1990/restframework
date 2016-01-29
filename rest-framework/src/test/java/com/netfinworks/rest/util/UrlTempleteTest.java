/**
 * 
 */
package com.netfinworks.rest.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author knico
 * @since Sep 29, 2012
 * 
 */
public class UrlTempleteTest {
	@Test
	public void test() {
		String url1 = "/sys/124141241/resource";
		String url2 = "/sys/124141241/role/34225252A/resource";
		UriTemplate template = new UriTemplate("/sys/{sysId}/resource", UrlMatchKind.Greedy);
		Assert.assertTrue(url1, template.matches(url1));
		Assert.assertTrue(url2, template.matches(url2));
		template = new UriTemplate("/sys/{sysId}/role/{roleId}/resource", UrlMatchKind.Cautious);
		Assert.assertFalse(url1, template.matches(url1));
		Assert.assertTrue(url2, template.matches(url2));
	}
}
