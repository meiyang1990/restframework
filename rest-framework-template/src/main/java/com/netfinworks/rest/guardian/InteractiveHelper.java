/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.guardian;

import javax.servlet.http.Cookie;

import com.netfinworks.rest.filter.RawHttpHolder;

/**
 * <p>前后台交互</p>
 * @author huipeng
 * @version $Id: InteractiveHelper.java, v 0.1 Apr 16, 2014 1:31:21 PM knico Exp $
 */
public class InteractiveHelper {

    private static ThreadLocal<String> topMenu         = new ThreadLocal<String>();
    /**
     * 
     */
    private static final String        COOKIE_CURRMENU = "_currmenu";

    /**
     * @return
     */
    public static String getTopMenuCode() {
        String ret = topMenu.get();
        if (ret == null) {
            Cookie cookie = RawHttpHolder.getCookie(COOKIE_CURRMENU + 0);
            if (cookie != null) {
                ret = cookie.getValue();
            }
        }
        return ret;
    }

    public static void setTopMenuCode(String code) {
        Cookie cookie = new Cookie(COOKIE_CURRMENU + 0, code);
        if (code == null || code.length() == 0) {
            cookie.setMaxAge(0);
        } else {
            topMenu.set(code);
        }
        RawHttpHolder.setCookie(cookie);
    }
}
