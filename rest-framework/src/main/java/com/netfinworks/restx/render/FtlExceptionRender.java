/*
 * Copyright 2013 weibopay.com, Inc. All rights reserved.
 */
package com.netfinworks.restx.render;

import java.util.HashMap;
import java.util.Map;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.restx.resp.CommonPage;

/**
 * <p>基于freemarker的异常渲染器</p>
 * @author zhangjiewen
 * @version $Id: FtlExceptionRender.java, v 0.1 2013-3-14 上午10:48:20 zhangjiewen Exp $
 */
public class FtlExceptionRender extends FtlFragmentRender {
    private static final String commonErrPage = "/commonError.html";
    private String errFolder = "err";
    public String getErrFolder() {
        return errFolder;
    }

    public void setErrFolder(String errFolder) {
        this.errFolder = errFolder;
    }

    private Map<String,String> errorPageMap = new HashMap<String, String>();
    public Map<String, String> getErrorPageMap() {
        return errorPageMap;
    }

    public void setErrorPageMap(Map<String, String> errorPageMap) {
        this.errorPageMap = errorPageMap;
    }

    @Override
    public Response render(Object resultObject, Request request) {
        throw new IllegalAccessError("un implemented method!");
    }

    @Override
    public Response renderException(Throwable exception, Request request) {
        return super.render(exception, request);
    }
    @Override
    protected Object generateResultObject(Object resultObject, Request request){
        CommonPage<Object> page = new CommonPage<Object>();
        page.setData(resultObject);
        return page;
    }
    @Override
    protected String generateTemplate(Object resultObject, Request request){
        String fragmentTemplate = errorPageMap.get(resultObject.getClass().getSimpleName());
        if(fragmentTemplate == null){
            fragmentTemplate = errFolder + commonErrPage;
        }
        return fragmentTemplate;
    }
}
