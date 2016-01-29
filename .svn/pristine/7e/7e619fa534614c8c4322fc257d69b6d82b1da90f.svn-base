package com.netfinworks.rest.server;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.UrlMatchKind;
/**
 * @author bigknife
 *
 */
public abstract interface IRestServer
{
	/**
	 * 注册web resource
	 * @param url
	 * @param matchKind
	 * @param bean
	 */
	void registerWebResource(String url, UrlMatchKind matchKind, Object bean);
	
	/**
	 * 初始化
	 */
	void init();

	/**
	 * 销毁
	 */
	void destroy();
/**
 * 处理请求
 * @param paramRequest
 * @return
 */
  Response serve(Request paramRequest);
}
