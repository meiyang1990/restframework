/**
 * 
 */
package com.netfinworks.restx.persist.jdbc;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 基础DO对象
 * 
 * @author bigknife
 * 
 */
public class DOBase implements HasID<String>, HasStatus<String>, BeTraced,Serializable {
    private static final long serialVersionUID = -8159220928911036093L;
    private String creator, modifier, status, id;
	private Date gmtCreate, gmtModified;
	
	@Override
	public Date getGmtModified() {
        return gmtModified;
    }
	@Override
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }
    @Override
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
	public String getCreator() {
		return creator;
	}

	@Override
	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Override
	public String getModifier() {
		return modifier;
	}

	@Override
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		try {
			Map props = BeanUtils.describe(this);
			Iterator iProps = props.keySet().iterator();
			StringBuffer retBuffer = new StringBuffer();
			retBuffer.append(this.getClass().getSimpleName()).append("@").append(this.hashCode())
					.append(":");
			while (iProps.hasNext()) {
				String key = (String) iProps.next();

				// skip false property "class"
				if ("class".equals(key)) {
					continue;
				}

				retBuffer.append(key).append("=[").append(props.get(key)).append("]");

				if (iProps.hasNext()) {
					retBuffer.append(", ");
				}
			}

			return retBuffer.toString();
		} catch (Exception e) {
			return super.toString();
		}
	}

}
