/**
 * 
 */
package com.netfinworks.restx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.restx.persist.jdbc.BasicCrudDAO;
import com.netfinworks.restx.persist.jdbc.DOBase;
import com.netfinworks.restx.persist.jdbc.PageInfo;
import com.netfinworks.restx.persist.jdbc.QueryConditionBase;

/**
 * <p>
 * 提供基本crud操作的service类
 * </p>
 * 
 * @author zhangjiewen
 * @version $Id: AbstractService.java, v 0.1 2012-9-20 上午10:11:02 zhangjiewen Exp $
 */
public abstract class ServiceBase<DOType extends DOBase, IDType, ConditionType extends QueryConditionBase> implements
		IService<DOType, IDType, ConditionType> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	protected Logger getLogger() {
		return this.logger;
	}

	public abstract BasicCrudDAO<DOType, IDType, ConditionType> getDAO();

	@Override
	public boolean create(DOType toCreate) throws ServiceException {
		return doCreate(toCreate);
	}

	@Override
	public QueryResult<DOType> list(ConditionType condition) throws ServiceException {
		QueryResult<DOType> queryResult = new QueryResult<DOType>();
		try {
			queryResult.setItems(this.getDAO().query(condition));
			// set page information
			queryResult.setPageInfo(condition == null ? PageInfo.EMPTY : condition.generatePageInfo());
			if (condition != null && condition.isCountTotal()) {
				// count total
				if (queryResult.getPageInfo() == PageInfo.EMPTY) {
					PageInfo pInfo = new PageInfo();
					pInfo.setCurrentPage(1);
					pInfo.setTotalCount(queryResult.getItems().size());
					if (pInfo.getTotalCount() > 0) {
						pInfo.setPageSize(pInfo.getTotalCount());
					}
				} else {
					queryResult.getPageInfo().setTotalCount(this.getDAO().count(condition));
				}
			}
		} catch (Exception e) {
			String msg = this.getClass().getSimpleName() + " list by do error.";
			logger.error(msg, e);
			throw new ServiceException(msg, e);
		}
		return queryResult;
	}

	@Override
	public int count(ConditionType condition) throws ServiceException {
		return this.getDAO().count(condition);
	}

	@Override
	public DOType findById(IDType id) throws ServiceException {
		DOType ret = null;
		try {
			logger.debug("findById id {}", id);
			ret = this.getDAO().findById(id);
			logger.debug("findById end.ret is {}", ret);
		} catch (Exception e) {
			logger.error("findById error.", e);
			throw new ServiceException("findById error.", e);
		}
		return ret;
	}

	@Override
	public boolean updateById(DOType toUpdate) throws ServiceException {
		return doUpdate(toUpdate);
	}

	@Override
	public boolean deleteById(IDType id) throws ServiceException {
		return doDelete(id);
	}

	protected boolean doDelete(IDType id) throws ServiceException {
		boolean ret = false;
		try {
			logger.debug("delete id is {}", id);
			int result = this.getDAO().deleteById(id);
			logger.debug("delete end.count is {}", result);
			ret = result == 1;
		} catch (Exception e) {
			logger.error("delete error.", e);
			throw new ServiceException("delete error.", e);
		}
		return ret;
	}

	protected boolean doUpdate(DOType toUpdate) throws ServiceException {
		boolean ret = false;
		try {
			logger.debug("update {}", toUpdate);
			int result = this.getDAO().updateById(toUpdate);
			logger.debug(toUpdate.getClass().getSimpleName() + " update end.count is {}", result);
			ret = result == 1;
		} catch (Exception e) {
			logger.error(toUpdate.getClass().getSimpleName() + " update error.", e);
			throw new ServiceException(toUpdate.getClass().getSimpleName() + " update error.", e);
		}
		return ret;
	}

	protected boolean doCreate(DOType toCreate) throws ServiceException {
		boolean ret = false;
		try {
			logger.debug("insert {}", toCreate);
			int result = this.getDAO().insert(toCreate);
			logger.debug(toCreate.getClass().getSimpleName() + " insert end.count is {}", result);
			ret = result == 1;
		} catch (Exception e) {
			logger.error(toCreate.getClass().getSimpleName() + " insert error.", e);
			throw new ServiceException(toCreate.getClass().getSimpleName() + " insert error.", e);
		}
		return ret;
	}
}
