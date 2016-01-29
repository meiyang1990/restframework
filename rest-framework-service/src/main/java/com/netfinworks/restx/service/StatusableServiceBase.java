/**
 * 
 */
package com.netfinworks.restx.service;

import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.HasStatus;
import com.netfinworks.restx.persist.jdbc.QueryConditionBase;

/**
 * @author knico
 * @since Oct 8, 2012
 * 
 */
public abstract class StatusableServiceBase<DOType extends DOBase & HasStatus<String>, ConditionType extends QueryConditionBase> extends
		ServiceBase<DOType, String, ConditionType> {
	private static final String DEL_STATUS = "deleted";
	public abstract DOType createNewDO();

	@Override
	public boolean deleteById(String id) throws ServiceException {
		this.getLogger().info("doDelete begin.");
		DOType vo = this.createNewDO();
		vo.setId(id);
		vo.setStatus(getDeletedStatus());
		int count = getDAO().updateById(vo);
		if (count == 0) {
			this.getLogger().warn("没有找到 id 为" + id + "的对象");
			return false;
		}
		return true;
	}
	
	protected String getDeletedStatus(){
		return DEL_STATUS;
	}
}
