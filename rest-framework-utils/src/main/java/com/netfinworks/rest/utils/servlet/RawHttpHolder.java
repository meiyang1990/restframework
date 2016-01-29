/**
 * 
 */
package com.netfinworks.rest.utils.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>原始的HTTP请求Holder</p>
 * 
 * @author huipeng
 * @version $Id: RawHttpHolder.java, v 0.1 Jun 26, 2014 2:51:28 PM knico Exp $
 */
public class RawHttpHolder {
    private static ThreadLocal<HttpServletRequest>  requestTl  = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> responseTl = new ThreadLocal<HttpServletResponse>();

    /**
     * 设置请求
     */
    static void setHttpServletRequest(HttpServletRequest req) {
        requestTl.set(req);
    }

    /**
     * 设置响应
     */
    static void setHttpServletResponse(HttpServletResponse resp) {
        responseTl.set(resp);
    }

    /**
     * 清楚ThreadLocal
     */
    static void clear() {
        requestTl.remove();
        responseTl.remove();
    }

    /**
     * 设置cookie
     * 
     * @param cookie
     */
    public static void setCookie(Cookie cookie) {
        HttpServletResponse response = responseTl.get();
        if (response != null) {
            response.addCookie(cookie);
        }
    }

    /**
     * 获取cookie
     */
    public static Cookie[] getCookies() {
        HttpServletRequest request = requestTl.get();
        if (request != null) {
            return request.getCookies();
        }
        return null;
    }

    public static Cookie getCookie(String key) {
        Cookie[] cookies = getCookies();
        Cookie ret = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    ret = cookie;
                    break;
                }
            }
        }
        return ret;
    }

    public static HttpServletRequest getServletRequest() {
        return requestTl.get();
    }
}
