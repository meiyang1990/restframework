/**
 * 
 */
package com.netfinworks.rest.util;

import java.lang.reflect.Array;

import com.netfinworks.rest.convert.IParamConvert;

/**
 * 类型转换工具
 * 
 * @author bigknife
 * 
 */
public abstract class ConvertUtil {
	/**
	 * 将字符串数组，使用IParamConvert实例转换为数组对象
	 * 
	 * @param rawValues
	 *            原始值 非NULL
	 * @param type
	 *            目标数组类型 非NULL
	 * @param encoding
	 *            字符编码 非NULL
	 * @return 数组对象
	 */
	public static Object convertUrlEncodedStringAsArray(String[] rawValues, String encoding,
			Class<?> type, IParamConvert convert) {
		if (type.isArray()) {
			Object array = Array.newInstance(type.getComponentType(),
					rawValues.length);
			for (int i = 0; i < rawValues.length; i++) {
				String rawValue = rawValues[i];
				rawValue = Encoding.decodeUrlEncodedString(rawValue, encoding);
				Array.set(array, i,
						convert.convert(rawValue, type.getComponentType()));
			}
			return array;
		}
		// 如果不是数组，则按单个对象处理
		return convert.convert(rawValues.length == 0 ? Magic.EmtpyString
				: Encoding.decodeUrlEncodedString(rawValues[0], encoding), type);
	}
	
	public static <T> Object addUrlEncodedStringIfArray(Object oldValues,String rawValue,String encoding,Class<?> type,IParamConvert convert) {
	    if (type.isArray()) {	        
	        int length = 0;
	        if (oldValues != null){
	            length = Array.getLength(oldValues);
	        }
            Object array = Array.newInstance(type.getComponentType(),
                length+1);
            if (oldValues != null){
                System.arraycopy(oldValues, 0, array, 0, length);
            }
            rawValue = Encoding.decodeUrlEncodedString(rawValue, encoding);
            Array.set(array, length,
            convert.convert(rawValue, type.getComponentType()));
            return array;
        }
        // 如果不是数组，则按单个对象处理
        return convert.convert(Encoding.decodeUrlEncodedString(rawValue, encoding), type);
	}
}
