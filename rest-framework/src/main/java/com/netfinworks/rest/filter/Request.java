package com.netfinworks.rest.filter;

import com.netfinworks.rest.enums.HttpVerb;
import com.netfinworks.rest.util.FormDecoder;
import com.netfinworks.rest.util.SafeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * rest 请求封装对象
 * 
 * @author bigknife
 * 
 */
public class Request {
    private Map<String, String>   headers         = new HashMap<String, String>();
    private String                queryString;
    private Map<String, String[]> queryParameters = new HashMap<String, String[]>();
    private String                url;
    private String                urlTemplate;
    private HttpServletRequest    rawRequest;

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public HttpVerb getHttpVerb() {
        return HttpVerb.valueOf(this.rawRequest.getMethod().toUpperCase());
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;

        this.queryParameters = FormDecoder.decode(queryString);
    }

    public String getQueryString() {
        return this.queryString;
    }

    public Map<String, String[]> getQueryParameters() {
        return this.queryParameters;
    }

    public String getUrlTemplate() {
        return this.urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    public void addHeader(String name, String value) {
        // 可能的参数入口，防止XSS攻击
        this.headers.put(name.toLowerCase(), SafeUtil.safeString(value));
    }

    public void addQueryParameters(String name, String[] values) {
        this.queryParameters.put(name, values);
    }

    public void addQueryParameters(Map<String, String[]> parameters) {
        if (parameters != null)
            this.queryParameters.putAll(parameters);
    }

    public String[] getQueryParameter(String name) {
        return (String[]) this.queryParameters.get(name);
    }

    public InputStream getInputStream() throws IOException {
        return this.rawRequest.getInputStream();
    }

    public String getFirstQueryParameter(String name) {
        String[] values = (String[]) this.queryParameters.get(name);
        if ((values != null) && (values.length > 0)) {
            return values[0];
        }
        return null;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCookie(String name) {
        String ret = null;
        Cookie[] cookies = rawRequest.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), name)) {
                    // 防XSS攻击
                    ret = SafeUtil.safeString(cookie.getValue());
                    break;
                }
            }
        }
        return ret;
    }

    public HttpServletRequest getRawRequest() {
        return this.rawRequest;
    }

    public void setRawRequest(HttpServletRequest rawRequest) {
        this.rawRequest = rawRequest;
    }
}
