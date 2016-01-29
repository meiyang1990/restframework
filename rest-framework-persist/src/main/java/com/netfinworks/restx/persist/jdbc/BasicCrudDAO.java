/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

import java.util.List;
/**
 * <p>
 * 提供基础的增删改查DAO接口
 * </p>
 * 
 * @author zhangjiewen
 */
public interface BasicCrudDAO<DOType extends DOBase, IDType, ConditionType extends QueryConditionBase> {
	public int insert(DOType object) throws PersistException;
	public DOType findById(IDType id) throws PersistException;
	public List<DOType> query(ConditionType condition) throws PersistException;
	public int updateById(DOType object) throws PersistException;
	public int deleteById(IDType id) throws PersistException;
	public int count(ConditionType condition) throws PersistException;
}
