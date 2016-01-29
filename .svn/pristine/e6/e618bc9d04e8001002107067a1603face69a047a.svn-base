/**
 * 
 */
package com.netfinworks.rest.template.resource;

import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.QueryConditionBase;
import com.netfinworks.restx.resp.CommonPage;
import com.netfinworks.restx.service.ServiceBase;

/**
 * @author knico
 * @since Mar 1, 2013
 * 
 */
public abstract class SingleResBase<T extends DOBase> extends ResBase {
	public abstract ServiceBase<T, String, ? extends QueryConditionBase> getService();

	public CommonPage<T> doGet(String id) {
		CommonPage<T> page = new CommonPage<T>();
		page.setData(getService().findById(id));
		onGet(page);
		return page;
	}

	/**
	 * @param page
	 */
	protected void onGet(CommonPage<T> page) {
	}

	protected final CommonPage<Boolean> doUpdate(String id, T updDO) {
		CommonPage<Boolean> page = new CommonPage<Boolean>();
		normalizeUpdating(updDO);
		if (onUpdating(id, updDO)) {
			updDO.setId(id);
			page.setData(getService().updateById(updDO));
			onUpdated(page);
		} else {
			page.setData(false);
		}
		return page;
	}

	/**
	 * 在调用数据库前，之后自动将id填充进去
	 * 
	 * @param id
	 * 
	 * @param updDO
	 * @return true while begin update, false directly return false
	 */
	protected boolean onUpdating(String id, T updDO) {
		return true;
	}

	/**
	 * 在调用数据库之後，返回結果之前
	 * 
	 * @param page
	 */
	protected void onUpdated(CommonPage<Boolean> page) {
	}

	public CommonPage<Boolean> doDelete(String id) {
		CommonPage<Boolean> page = new CommonPage<Boolean>();
		if (onDeleting(id)) {
			page.setData(getService().deleteById(id));
			onDeleted(page);
		} else {
			page.setData(false);
		}
		return page;
	}

	/**
	 * 删除数据之前，默認總是允許
	 * 
	 * @param id
	 * @return
	 */
	protected boolean onDeleting(String id) {
		return true;
	}

	/**
	 * 數據刪除之後
	 * 
	 * @param page
	 */
	protected void onDeleted(CommonPage<Boolean> page) {
	}

	protected void normalizeUpdating(T updDO) {
		updDO.setModifier(this.getCurrentUser().getLoginName());
	}
}
