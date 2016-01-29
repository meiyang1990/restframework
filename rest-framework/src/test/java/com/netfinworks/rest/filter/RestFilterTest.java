/**
 * 
 */
package com.netfinworks.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

/**
 * @author bigknife
 * 
 */
// extends AbstractJUnit4SpringContextTests
public class RestFilterTest {
	private static Filter filter;
	private static MockFilterConfig fc;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	@BeforeClass
	public static void init() throws ServletException {
		fc = new MockFilterConfig();
		filter = new RestFilter();
		filter.init(fc);
	}

	@AfterClass
	public static void destroy() {
		filter.destroy();
	}

	@Before
	public void before() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Test
	public void testUrlParam() throws Exception {
		request.setMethod(HttpVerb.GET.toString());
		response.setOutputStreamAccessAllowed(true);
		request.setRequestURI("/order/1");

		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		String output = "{\"id\":\"1\",\"name\":\"test order, 中文支持测试\",\"amount\":1000}";
		Assert.assertEquals(s, output);

	}

	@Test
	public void testBodyAsString() throws IOException, ServletException {
		request.setRequestURI("/order/1");
		request.setMethod(HttpVerb.PUT.toString());
		String body = "id=1&name=test&amount=1000";
		request.setContent(body.getBytes());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);

		String s = response.getContentAsString();
		String output = "this is a exception test!";
		Assert.assertEquals(s, output);
	}

	@Test
	public void testBodyAsStream() throws IOException, ServletException {
		request.setRequestURI("/stream");
		String body = "id=1&name=test&amount=1000";
		request.setContent(body.getBytes());
		request.setMethod(HttpVerb.POST.toString());
		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		String output = "\"id=1&name=test&amount=1000\"";
		Assert.assertEquals(s, output);
	}

	@Test
	public void testQueryParamAsArray() throws IOException, ServletException {
		request.setRequestURI("/string/array");
		request.setQueryString("idList=1&idList=2&idList=3");
		request.setMethod(HttpVerb.GET.toString());

		response = new MockHttpServletResponse();
		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();

		String output = "{\"test\":[1,2,3]}";

		Assert.assertEquals(s, output);
	}

	@Test
	public void testNoParamResource() throws Exception {
		request.setRequestURI("/noparam");
		request.setMethod(HttpVerb.GET.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();

		String output = "\"hello,world\"";

		Assert.assertEquals(s, output);
	}

	@Test
	public void testNoFoundResource() throws Exception {
		request.setRequestURI("/noparam/nothing");
		request.setMethod(HttpVerb.GET.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 404);
		Assert.assertEquals(s, Magic.EmtpyString);
	}

	@Test
	public void testNotAuthorizedResource() throws Exception {
		request.setRequestURI("/auth/resource");
		request.setMethod(HttpVerb.GET.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 401);
		Assert.assertEquals(s, Magic.EmtpyString);
	}

	@Test
	public void testAuthorizedResource() throws Exception {
		request.setRequestURI("/auth/resource");
		request.setMethod(HttpVerb.GET.toString());
		request.addHeader(HttpHeaderName.Authorization,
				"Basic " + Base64.encodeBase64String("bigknife:123456".getBytes()));

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(s, "\"This is secret\"");
	}

	@Test
	public void testResourceWithRenderBean() throws Exception {
		request.setRequestURI("/renderWithBean");
		request.setMethod(HttpVerb.GET.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(s, "CONSTANT");
	}

	@Test
	public void testAuditResource() throws Exception {
		request.setRequestURI("/audit");
		request.setMethod(HttpVerb.GET.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(s, "\"hello,world\"");
	}

	@Test
	public void testAuditResourceWithAuditRef() throws Exception {
		request.setRequestURI("/audit");
		request.setMethod(HttpVerb.POST.toString());

		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 403);
		Assert.assertEquals(s, "");
	}

	@Test
	public void testHeaderParam() throws Exception {
		request.setRequestURI("/headerparam");
		request.setMethod(HttpVerb.GET.toString());
		String contentType = "text/plain";
		request.addHeader("Content-Type", contentType);
		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		String s = response.getContentAsString();
		Assert.assertEquals(response.getStatus(), 200);
		Assert.assertEquals(s, "\"hello,world" + contentType + "\"");
	}

	@Test
	public void testResourceExcpeption() throws Exception {
		request.setRequestURI("/resourceexception");
		request.setMethod(HttpVerb.GET.toString());
		String contentType = "text/plain";
		request.addHeader("Content-Type", contentType);
		response.setOutputStreamAccessAllowed(true);
		filter.doFilter(request, response, null);
		int status = response.getStatus();
		Assert.assertEquals(status, HttpStatus.BAD_REQUEST.value());
		Assert.assertEquals("test reource exception", response.getContentAsString());
	}
}
