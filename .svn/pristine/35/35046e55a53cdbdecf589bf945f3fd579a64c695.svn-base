/**
 * 
 */
package com.netfinworks.rest.guardian;

import com.netfinworks.guardian.sso.filter.GuardianUser;
import com.netfinworks.guardian.sso.filter.GuardianUser.User;
import com.netfinworks.rest.render.FrameDataProvider;

/**
 * 当前用户数据Provider
 * 
 * @author bigknife
 * 
 */
public class CurrentUserDataProvider implements FrameDataProvider {
	@Override
	public Object provide() {
		User user = GuardianUser.get();
		if (user != null) {
			String currentToken = GuardianUser.getCurrentToken();
			CurrentUserData cud = new CurrentUserData();
			cud.setLoginName(user.getLoginName());
			cud.setName(user.getName());
			cud.setToken(currentToken);

			return cud;
		}
		return null;
	}

}
