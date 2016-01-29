/**
 * 
 */
package com.netfinworks.rest.annotation;

import com.netfinworks.rest.convert.IParamConvert;

/**
 * @author bigknife
 *
 */
public class UrlParamAnnotationData {
	private String name;
	private String encoding;
	private Class<IParamConvert> converter;
	private String converterRef;
	
	
	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the converter
	 */
	public Class<IParamConvert> getConverter() {
		return converter;
	}
	/**
	 * @param converter the converter to set
	 */
	public void setConverter(Class<IParamConvert> converter) {
		this.converter = converter;
	}
	/**
	 * @return the converterRef
	 */
	public String getConverterRef() {
		return converterRef;
	}
	/**
	 * @param converterRef the converterRef to set
	 */
	public void setConverterRef(String converterRef) {
		this.converterRef = converterRef;
	}
	
	
}
