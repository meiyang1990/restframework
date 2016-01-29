package com.netfinworks.rest.auth;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;
/**
 * basic authorization，用户名和密码的校验委托给IUserPasswordCheck接口
 * @see {@link IUserPasswordCheck}
 * @author bigknife
 *
 */
public class BasicAuthCheck implements IAuthCheck {
	private IUserPasswordCheck userPasswordCheck = new SimpleUserPasswordCheck();
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * @param userPasswordCheck the userPasswordCheck to set
	 */
	public void setUserPasswordCheck(IUserPasswordCheck userPasswordCheck) {
		logger.info("set user password check:" + userPasswordCheck);
		this.userPasswordCheck = userPasswordCheck;
	}

	public AuthCheckResult checkAuth(Map<String, String> headers) {
		String authorization = (String) headers.get("authorization");
		if (authorization != null) {
			String[] authInfos = authorization.split(Magic.OneBlank);
			if (authInfos.length != 2) {
				return refused();
			}
			if (!Magic.Basic.equals(authInfos[0])) {
				return refused();
			}
			String userPwdBase64 = authInfos[1].trim();
			if (userPwdBase64.length() == 0) {
				return refused();
			}

			String userPwd = new String(Base64.decodeBase64(userPwdBase64));
			String[] userAndPassword = userPwd.split(Magic.Colon);
			if (checkUserPassword(userAndPassword[0], userAndPassword[1])) {
				return AuthCheckResult.authorizedInstance();
			}
		}
		return refused();
	}

	private AuthCheckResult refused() {
		AuthCheckResult result = AuthCheckResult.unauthorizedInstance();
		result.addResponseHeader(HttpHeaderName.WWW_Authenticate,Magic.BasicAuthInfo);
		return result;
	}

	private boolean checkUserPassword(String user, String password) {
		return this.userPasswordCheck.checkUserPassword(user, password);
	}
	/**
	 * 用户密码校验
	 * @author bigknife
	 *
	 */
	public static abstract interface IUserPasswordCheck {
		public abstract boolean checkUserPassword(String user,
				String password);
	}
}
