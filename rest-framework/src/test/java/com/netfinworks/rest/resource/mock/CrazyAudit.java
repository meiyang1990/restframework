/**
 * 
 */
package com.netfinworks.rest.resource.mock;

import java.util.Map;

import com.netfinworks.rest.audit.AuditResult;
import com.netfinworks.rest.audit.IResourceAudit;
import com.netfinworks.rest.enums.HttpVerb;

/**
 * @author bigknife
 *
 */
public class CrazyAudit implements IResourceAudit {

	/* (non-Javadoc)
	 * @see com.netfinworks.rest.audit.IResourceAudit#audit(java.lang.String, com.netfinworks.rest.enums.HttpVerb, java.util.Map)
	 */
	@Override
	public AuditResult audit(String url, String urlTemplate, HttpVerb verb,
			Map<String, String> headers) {
		return AuditResult.incompliantAuditResult();
	}

}
