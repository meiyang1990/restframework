/**
 * 
 */
package com.netfinworks.restx.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 全局的LogBack日志配置文件加载，当有其他filter在restframework之前启动时，可以配置这个listener以指定确切的配置文件
 * @author bigknife
 *
 */
public class LogbackConfigurationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String logbackConf = sce.getServletContext().getInitParameter("logbackConfiguration");
		try {
			initLogback(logbackConf);
		} catch (JoranException e) {
			throw new RuntimeException("Logback 初始化失败",e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	private void initLogback(String logbackConfigFile) throws JoranException {

        File file = new File(logbackConfigFile);
        if (!file.exists() || file.isDirectory()) {
        	Logger logger = LoggerFactory.getLogger(getClass());
            logger.error("logback配置文件不存在:" + logbackConfigFile);
        } else {
            JoranConfigurator configurator = new JoranConfigurator();
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            loggerContext.reset();
            configurator.setContext(loggerContext);
            configurator.doConfigure(file);
            StatusPrinter.print(loggerContext);
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("logback配置文件指定为：{}", new String[]{logbackConfigFile});
        }


    }

}
