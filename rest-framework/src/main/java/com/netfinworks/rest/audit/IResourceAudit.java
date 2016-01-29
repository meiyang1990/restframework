/**
 * 
 */
package com.netfinworks.rest.audit;

import java.util.Map;

import com.netfinworks.rest.enums.HttpVerb;

/**
 * 资源访问审计接口
 * @author bigknife
 *
 */
public interface IResourceAudit {
	/**
	 * 对资源进行审计
	 * @param url 具体的资源实体
	 * @param urlTemplate 资源url模板
	 * @param headers 请求头
	 * @return
	 */
	AuditResult audit(String url, String urlTemplate,HttpVerb verb, Map<String, String> headers);
}
