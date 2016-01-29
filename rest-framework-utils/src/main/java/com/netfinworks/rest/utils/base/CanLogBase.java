/**
 * Copyright 2013 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>...</p>
 * @author huipeng
 * @version $Id: CanLogBase.java, v 0.1 Nov 20, 2013 6:08:40 PM knico Exp $
 */
public class CanLogBase {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }
}
