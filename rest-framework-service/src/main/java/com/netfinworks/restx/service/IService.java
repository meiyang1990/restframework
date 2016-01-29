/**
 * 
 */
package com.netfinworks.restx.service;

import com.netfinworks.restx.persist.jdbc.DOBase;

/**
 * 服务接口
 * 
 * @author bigknife
 * 
 */
public interface IService<DOType extends DOBase, IDType, ConditionType> {
	public boolean create(DOType toCreate) throws ServiceException;

	public DOType findById(IDType id) throws ServiceException;

	public boolean deleteById(IDType id) throws ServiceException;

	public boolean updateById(DOType toUpdate) throws ServiceException;

	public QueryResult<DOType> list(ConditionType condition) throws ServiceException;

	public int count(ConditionType condition) throws ServiceException;
}
