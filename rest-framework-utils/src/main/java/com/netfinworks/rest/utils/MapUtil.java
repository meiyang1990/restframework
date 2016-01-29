/**
 * Copyright 2015 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

/**
 * <p>Map相关工具</p>
 * @author huipeng
 * @version $Id: MapUtil.java, v 0.1 Jul 23, 2015 1:34:20 PM knico Exp $
 */
public class MapUtil {

    private static final TypeReference<HashMap<String, String>> MAP_TYPE_REF = new TypeReference<HashMap<String, String>>() {
                                                                             };

    public static String writeAsString(Map<String, String> map) {
        try {
            return EmptyUtil.isEmpty(map) ? null : JsonConverter.me().writeValueAsString(map);
        } catch (Exception e) {
            throw new RuntimeException("Map to String failed.", e);
        }
    }

    public static Map<String, String> parseString(String str) {
        Map<String, String> ret = null;
        if (!EmptyUtil.isEmpty(str)) {
            try {
                ret = JsonConverter.me().readValue(str, MAP_TYPE_REF);
            } catch (Exception e) {
                LoggerUtil.e(e, "Parse map failed.");
            }
        }
        return ret;
    }

}
