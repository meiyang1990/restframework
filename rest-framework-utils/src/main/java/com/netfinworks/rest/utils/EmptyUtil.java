package com.netfinworks.rest.utils;

import java.util.Collection;
import java.util.Map;

/**
 * <p>空工具类</p>
 * @author huipeng
 * @version $Id: EmptyUtil.java, v 0.1 Oct 11, 2013 10:27:02 AM knico Exp $
 */
public class EmptyUtil {
    public static boolean isEmpty(CharSequence charSequence) {
        if ((charSequence == null) || (charSequence.length() == 0)) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return EmptyUtil.isEmpty(str, false);
    }

    public static boolean isEmpty(String str, boolean trim) {
        if ((str == null) || ((trim ? str.trim() : str).length() == 0)) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Long l) {
        return EmptyUtil.isEmpty(l, false);
    }

    public static boolean isEmpty(Long l, boolean zero) {
        if ((l == null) || (zero && (l.longValue() == 0))) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection<?> c) {
        if ((c == null) || c.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if ((map == null) || map.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] objs) {
        if ((objs == null) || (objs.length == 0)) {
            return true;
        }
        return false;
    }
}
