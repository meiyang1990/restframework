/**
 * 
 */
package com.netfinworks.rest.template.util;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Json 工具类
 * @author bigknife
 *
 */
public class JsonUtil {
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper om = new ObjectMapper();
	public static String serialize(Object resp) {
		try {
			return om.writeValueAsString(resp);
		} catch (Exception e) {
			throw new RuntimeException("json serialize error",e);
		} 
	}
	
	public static HashMap<String, String> toMap(String jsonStr){
		if(jsonStr == null || jsonStr.trim().length() == 0){
			return null;
		}
		try {
			return om.readValue(jsonStr,HashMap.class);
		} catch (Exception e) {
			logger.error("JsonUtil toMap error.",e);
			return null;
		} 
	}
}
