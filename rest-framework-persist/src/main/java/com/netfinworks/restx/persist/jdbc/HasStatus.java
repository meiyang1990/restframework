/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

/**
 * 有状态标志
 * @author bigknife
 *
 */
public interface HasStatus<StatusType> {
	StatusType getStatus();
	void setStatus(StatusType status);
}
