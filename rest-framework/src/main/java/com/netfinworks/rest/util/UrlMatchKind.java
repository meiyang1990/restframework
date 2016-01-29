/**
 * 
 */
package com.netfinworks.rest.util;

/**
 * @author knico
 * @since Sep 29, 2012
 * 
 */
public enum UrlMatchKind {
	/**
	 * Url中的数据可以匹配任何字符
	 */
	Greedy,
	/**
	 * url中匹配数据时不跨越“/”
	 */
	Cautious
}
