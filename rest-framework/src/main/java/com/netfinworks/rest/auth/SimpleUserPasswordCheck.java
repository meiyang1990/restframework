package com.netfinworks.rest.auth;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 简单用户密码检查，用户密码放在properties文件中，文件查找规则为：<br />
 * 1. 系统变量SimpleUserPasswordCheck.properties标志的地址<br />
 * 2. classpath:/SimpleUserPasswordCheck.properties
 * @author bigknife
 *
 */
class SimpleUserPasswordCheck implements BasicAuthCheck.IUserPasswordCheck {
	public static String SystemPropertyName = "SimpleUserPasswordCheck.properties";
	public static String defaultProperties = "/SimpleUserPasswordCheck.properties";
	private static Properties p = new Properties();
	

	public boolean checkUserPassword(String user, String password) {
		try {
			Properties prop = getProperties();
			String pwd = prop.getProperty(user);
			return (pwd != null) && (pwd.equals(password));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private Properties getProperties() throws IOException {
		if (p.isEmpty()) {
			String propertiesFile = System.getProperty(SystemPropertyName);
			if ((propertiesFile == null) || (propertiesFile.trim().length() == 0))
				p.load(getClass().getResourceAsStream(defaultProperties));
			else {
				p.load(new FileInputStream(propertiesFile.trim()));
			}
		}
		return p;
	}
}
