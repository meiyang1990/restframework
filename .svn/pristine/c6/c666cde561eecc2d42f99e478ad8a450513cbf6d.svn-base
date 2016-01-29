/**
 * 
 */
package com.netfinworks.rest.helper;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化一些系统变量
 * @author bigknife
 *
 */
public class SystemProperyInit {
	private Map<String, String> properties;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public void init(){
		if(properties != null){
			for(Entry<String, String> entry : properties.entrySet()){
				String name = entry.getKey();
				String value = entry.getValue();
				System.setProperty(name, value);
				logger.info("set system property: name={}, value={}",new Object[]{name, value});
			}
		}
	}
}
