/*
 * Copyright 2013 weibopay.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author knico
 * @since Sep 17, 2013
 * 
 */
public final class AppContextHolder {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ConfigurableApplicationContext applicationContext;
	private static AppContextHolder instance = new AppContextHolder();

	private AppContextHolder() {
	}

	public static AppContextHolder getInstanse() {
		return instance;
	}

	public void init(String[] contextResources) {
		logger.info("Application context initialize by:{}", contextResources);
		this.applicationContext = new ClassPathXmlApplicationContext(contextResources);
	}

	public ConfigurableApplicationContext getAppContext() {
		if (this.applicationContext == null) {
			logger.error("Application context not initialized yet!");
			throw new RuntimeException("Application context not initialized yet!");
		}
		return applicationContext;
	}

	/**
	 * 
	 */
	public void close() {
		if (applicationContext != null) {
			logger.info("Application context closed!");
			applicationContext.close();
		}
	}
}
