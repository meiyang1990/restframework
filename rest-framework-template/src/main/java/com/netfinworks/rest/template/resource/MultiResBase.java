/**
 * 
 */
package com.netfinworks.rest.template.resource;

import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.QueryConditionBase;
import com.netfinworks.restx.resp.CommonPage;
import com.netfinworks.restx.service.QueryResult;
import com.netfinworks.restx.service.ServiceBase;

/**
 * @author knico
 * @since Mar 1, 2013
 * 
 */
public abstract class MultiResBase<T extends DOBase, C extends QueryConditionBase> extends ResBase {
	public abstract ServiceBase<T, String, C> getService();

	protected final CommonPage<QueryResult<T>> doList(C cond) {
		CommonPage<QueryResult<T>> page = new CommonPage<QueryResult<T>>();
		onListing(cond);
		page.setData(this.getService().list(cond));
		onListed(page);
		return page;
	}

	/**
	 * 查询数据之前
	 * 
	 * @param cond
	 */
	protected void onListing(C cond) {
	}

	/**
	 * 查询数据列表之后
	 * 
	 * @param page
	 */
	protected void onListed(CommonPage<QueryResult<T>> page) {
	}
}
