/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

import java.io.Serializable;

/**
 * 表示有ID属性
 * @author bigknife
 *
 */
public interface HasID<IDType extends Serializable> {
	IDType getId();
	void setId(IDType id);
}
