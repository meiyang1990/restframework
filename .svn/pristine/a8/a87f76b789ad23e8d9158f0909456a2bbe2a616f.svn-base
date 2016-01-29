/**
 * 
 */
package com.netfinworks.rest.template.resource;

import com.netfinworks.rest.template.enums.StatusKind;
import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.QueryConditionBase;
import com.netfinworks.restx.resp.CommonPage;
import com.netfinworks.restx.service.ServiceBase;

/**
 * @author knico
 * @since Mar 1, 2013
 * 
 */
public abstract class SingleInitResBase<T extends DOBase> extends ResBase {
	public abstract ServiceBase<T, String, ? extends QueryConditionBase> getService();

	protected final  CommonPage<T> doGet() {
		CommonPage<T> page = new CommonPage<T>();
		page.setData(createNewDO());
		onGet(page);
		return page;
	}

	/**
	 * @return
	 */
	public abstract T createNewDO();

	/**
	 * @param page
	 */
	protected void onGet(CommonPage<T> page) {
	}

	protected final  CommonPage<Boolean> doCreate(T createDO) {
		CommonPage<Boolean> page = new CommonPage<Boolean>();
		normalizeCreating(createDO);
		onCreating(createDO);
		page.setData(getService().create(createDO));
		onCreated(page);
		return page;
	}

	/**
	 * @param createDO
	 */
	protected void onCreating(T createDO) {
	}

	/**
	 * @param page
	 */
	protected void onCreated(CommonPage<Boolean> page) {
	}

	protected void normalizeCreating(T createDO) {
		createDO.setCreator(this.getCurrentUser().getLoginName());
		createDO.setStatus(StatusKind.USING.getCode());
	}
}
