/**
 * 
 */
package com.netfinworks.rest.audit;

import java.io.InputStream;

import com.netfinworks.rest.enums.HttpStatus;
import com.netfinworks.rest.filter.ImmutableResponse;
import com.netfinworks.rest.filter.Response;

/**
 * 审查结果
 * @author bigknife
 *
 */
public class AuditResult {
	private boolean compliant; //是否审计合规
	private Response response;
	
	private AuditResult(){}
	
	/**
	 * 新建一个合规的审计结果对象
	 * @return
	 */
	public static AuditResult compliantAuditResult(){
		AuditResult result = new AuditResult();
		result.compliant = true;
		result.response = null;
		return result;
	}
	
	public static AuditResult incompliantAuditResult(){
		AuditResult result = new AuditResult();
		result.compliant = false;
		result.response = new Response();
		result.response.setStatus(HttpStatus.FORBIDDEN);
		return result;
	}
	public void setResponseInputStream(InputStream input){
		if(response != null){
			response.setInputStream(input);
		}
	}
	public void addResponseHeader(String name, String value){
		if(response != null){
			response.addHeader(name, value);
		}
	}
	public void setResponseStatus(HttpStatus status){
		if(response != null){
			response.setStatus(status);
		}
	}
	public Response getImmutableResponse(){
		return new ImmutableResponse(response);
	}
	public boolean isCompliant(){
		return compliant;
	}
}
