/**
 * 
 */
package com.netfinworks.rest.audit;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.enums.HttpVerb;

/**
 * 简单审计实现类，仅使用slf4j logger 的info级别打印访问日志
 * @author bigknife
 *
 */
public class SimpleResourceAudit implements IResourceAudit {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public AuditResult audit(String url,String urlTemplate, HttpVerb verb,
			Map<String, String> headers) {
		logger.info("{} {}",new Object[]{verb,urlTemplate});
		return AuditResult.compliantAuditResult();
	}

}
