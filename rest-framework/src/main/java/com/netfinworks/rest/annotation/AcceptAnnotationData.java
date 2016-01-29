/**
 * 
 */
package com.netfinworks.rest.annotation;

import com.netfinworks.rest.convert.IParamConvert;

/**
 * @author bigknife
 *
 */
public class AcceptAnnotationData {
	private String contentType;
	private Class<? extends IParamConvert> paramConvert;
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the paramConvert
	 */
	public Class<? extends IParamConvert> getParamConvert() {
		return paramConvert;
	}
	/**
	 * @param paramConvert the paramConvert to set
	 */
	public void setParamConvert(Class<? extends IParamConvert> paramConvert) {
		this.paramConvert = paramConvert;
	}
}
