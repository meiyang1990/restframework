/**
 * Copyright 2015 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils;

import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

/**
 * <p>Set工具</p>
 * @author huipeng
 * @version $Id: SetUtil.java, v 0.1 Jul 23, 2015 1:59:56 PM knico Exp $
 */
public class SetUtil {

    private static final TypeReference<HashSet<String>> SET_TYPE_REF = new TypeReference<HashSet<String>>() {
                                                                     };

    public static String writeAsString(Set<String> set) {
        try {
            return EmptyUtil.isEmpty(set) ? null : JsonConverter.me().writeValueAsString(set);
        } catch (Exception e) {
            throw new RuntimeException("Set to String failed.", e);
        }
    }

    public static Set<String> parseString(String str) {
        Set<String> ret = null;
        if (!EmptyUtil.isEmpty(str)) {
            try {
                ret = JsonConverter.me().readValue(str, SET_TYPE_REF);
            } catch (Exception e) {
                LoggerUtil.e(e, "Parse set failed.");
            }
        }
        return ret;
    }

}
