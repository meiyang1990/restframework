/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <p>Json</p>
 * @author huipeng
 * @version $Id: JsonConverter.java, v 0.1 Jun 4, 2014 3:29:27 PM knico Exp $
 */
public class JsonConverter {
    private static ObjectMapper om;

    public static ObjectMapper me() {
        if (om == null) {
            synchronized (JsonConverter.class) {
                if (om == null) {
                    om = new ObjectMapper()
                        .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
                }
            }
        }
        return om;
    }
}
