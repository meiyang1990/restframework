/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

import java.util.Date;

/**
 * 表示对象被跟踪
 * @author bigknife
 *
 */
public interface BeTraced {
	String getCreator();
	void setCreator(String creator);
	
	String getModifier();
	void setModifier(String modifier);
	
	public Date getGmtModified();

    public void setGmtModified(Date gmtModified);

    public Date getGmtCreate();

    public void setGmtCreate(Date gmtCreate);
	
}
