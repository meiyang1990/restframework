/**
 * 
 */
package com.netfinworks.rest.template.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.guardian.sso.filter.GuardianUser;
import com.netfinworks.guardian.sso.filter.GuardianUser.User;

/**
 * @author knico
 * @since Mar 1, 2013
 * 
 */
public abstract class ResBase {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected User getCurrentUser() {
		User user = GuardianUser.get();
		if (user == null) {
			logger.warn("GuardianUser.get() returns null.");
			user = new User();
		}
		return user;
	}
}
