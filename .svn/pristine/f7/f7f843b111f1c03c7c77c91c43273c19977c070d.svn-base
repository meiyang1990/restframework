/**
 * 
 */
package com.netfinworks.rest.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @author bigknife
 * 
 */
public class BasicAuthUtil {
	/**
	 * 从一个合法的basic authorization信息中解码用户名和密码
	 * 
	 * @param basicAuthorization
	 * @return <code>array[0] = user</code>; <code>array[1] = password</code>
	 */
	public static String[] getUserPassword(String basicAuthorization) {
		if (basicAuthorization != null) {
			String[] authInfos = basicAuthorization.split(Magic.OneBlank);
			if (authInfos.length != 2) {
				return null;
			}
			if (!Magic.Basic.equals(authInfos[0])) {
				return null;
			}
			String userPwdBase64 = authInfos[1].trim();
			if (userPwdBase64.length() == 0) {
				return null;
			}

			String userPwd = new String(Base64.decodeBase64(userPwdBase64));
			String[] userAndPassword = userPwd.split(Magic.Colon);
			return userAndPassword;
		}
		return null;
	}

}
