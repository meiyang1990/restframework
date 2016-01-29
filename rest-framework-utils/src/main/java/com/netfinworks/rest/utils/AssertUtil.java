/**
 * 
 */
package com.netfinworks.rest.utils;

/**
 * @author knico
 * @since Aug 27, 2012
 * 
 */
public final class AssertUtil {
	/**
	 * throw runtime-exception when object is null
	 * 
	 * @param name
	 * @param obj
	 */
	public static void assetNotNull(String name, Object obj) {
		if (obj == null) {
			throw new RuntimeException("The parameter '" + name + "' not allow be null!");
		}
	}

	/**
	 * throw runtime-exception when tow object not equal
	 * 
	 * @param name
	 * @param obj1
	 * @param obj2
	 */
	public static void assetEqual(String name, Object obj1, Object obj2) {
		if (obj1 != null && obj1.equals(obj2)) {
			return;
		} else if (obj1 == obj2) {
			return;
		}
		throw new RuntimeException("The objects(" + name + ") '" + obj1 + "' and '" + obj2 + "' not equal!");
	}
}
