/**
 * Copyright 2015 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: LoggerUtil.java, v 0.1 Jul 23, 2015 1:45:21 PM knico Exp $
 */
public class LoggerUtil {
    private static Logger logger;
    static {
        try {
            logger = LoggerFactory.getLogger("commonLogger");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(Throwable thr, String fmt, Object... args) {
        e(logger, thr, fmt, args);
    }

    public static void e(Logger log, Throwable thr, String fmt, Object... args) {
        if (log != null) {
            if (args == null) {
                log.error(fmt, thr);
            } else {
                log.error(MessageFormat.format(fmt, args), thr);
            }
        }
    }

    public static void i(String fmt, Object... args) {
        i(logger, fmt, args);
    }

    public static void i(Logger log, String fmt, Object... args) {
        if (log != null && log.isInfoEnabled()) {
            log.info(fmt, args);
        }
    }

    public static void d(String fmt, Object... args) {
        i(logger, fmt, args);
    }

    public static void d(Logger log, String fmt, Object... args) {
        if (log != null && log.isDebugEnabled()) {
            log.debug(fmt, args);
        }
    }

}
