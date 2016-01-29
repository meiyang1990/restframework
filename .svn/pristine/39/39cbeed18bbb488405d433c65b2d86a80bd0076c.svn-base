/**
 * 
 */
package com.netfinworks.rest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * @author bigknife
 *
 */
public class FormDecoder {
    private static final String and   = "&";
    private static final String eq    = "=";
    private static final String empty = "";

    /**
     * <h1>解码form表单形式的参数，参数的值是以utf-8做URLEncode</h1>
     * <b>form example:</b> <code>id=1&name=sb.&address=shanghai&address=newyork</code>
     * @param formEncodedString
     * @return
     */
    public static Map<String, String[]> decode(String formEncodedString) {
        if (formEncodedString != null) {
            String[] nameValuePairs = formEncodedString.split(and);
            Map<String, List<String>> tmpMap = new HashMap<String, List<String>>();
            for (String nameValuePair : nameValuePairs) {
                String[] nameValue = nameValuePair.split(eq);
                String name = nameValue[0];
                String value = nameValue.length > 1 ? nameValue[1] : empty;
                List<String> list = tmpMap.get(name);
                if (list == null) {
                    list = new ArrayList<String>();
                    tmpMap.put(name, list);
                }
                list.add(SafeUtil.safeEncodeString(value));
            }
            Map<String, String[]> ret = new HashMap<String, String[]>();
            for (String name : tmpMap.keySet()) {
                List<String> values = tmpMap.get(name);
                ret.put(name, StringUtils.toStringArray(values));
            }
            return ret;
        }
        return null;
    }
}
