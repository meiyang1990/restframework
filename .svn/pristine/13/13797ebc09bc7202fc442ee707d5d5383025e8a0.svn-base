package com.netfinworks.rest.auth;

import java.io.InputStream;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.ImmutableResponse;
import com.netfinworks.rest.filter.Response;

/**
 * 授权检查结果
 * @author bigknife
 *
 */
public class AuthCheckResult {
	private boolean passed;
	private Response response;

	private AuthCheckResult() {
	}

	public static AuthCheckResult authorizedInstance() {
		AuthCheckResult result = new AuthCheckResult();
		result.passed = true;
		result.response = null;
		return result;
	}

	public static AuthCheckResult unauthorizedInstance() {
		AuthCheckResult result = new AuthCheckResult();
		result.passed = false;
		result.response = new Response();
		result.response.setStatus(HttpStatus.UNAUTHORIZED);
		return result;
	}

	public boolean isPassed() {
		return this.passed;
	}
	
	public void setResponseInputStream(InputStream in){
		response.setInputStream(in);
	}
	public void addResponseHeader(String name, String value){
		if(response != null){
			response.addHeader(name, value);
		}
	}
	public Response getImmutableResponse(){
		return new ImmutableResponse(response);
	}
}
