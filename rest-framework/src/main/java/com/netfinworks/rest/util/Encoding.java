/**
 * 
 */
package com.netfinworks.rest.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author bigknife
 *
 */
public class Encoding {
	public static final String UTF_8 = "UTF-8";
	private static final byte[] emptyByteArray = new byte[]{};
	/**
	 * 以默认的utf-8解码string
	 * @param str
	 * @return
	 */
	public static byte [] decode(String str){
		try {
			return str == null ? emptyByteArray : str.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 以默认的utf-8解码string
	 * @param str
	 * @return
	 */
	public static byte [] decode(String str,String encoding){
		try {
			return str == null ? emptyByteArray : str.getBytes(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 以默认的utf-8解码string
	 * @param str
	 * @return
	 */
	public static String decodeUrlEncodedString(String str,String encoding){
		try {
		    // 防止XSS攻击
			return str == null ? null : SafeUtil.safeString(URLDecoder.decode(str, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
