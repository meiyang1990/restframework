/*
 * Copyright 2012 weibopay.com, Inc. All rights reserved.
 * weibopay.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netfinworks.restx.render;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.render.IRender;
import com.netfinworks.rest.util.HttpHeaderName;

/**
 * <h1>文件下载渲染器</h1>
 * <p>要求资源返回一个{@link FileWrapper}对象</p>
 * @author zhangjiewen
 * @version $Id: FileDownloadRender.java, v 0.1 2012-11-21 上午11:03:16 zhangjiewen Exp $
 */
public class FileDownloadRender implements IRender {
    @Override
    public Response render(Object resultObject, Request request) {
        Response resp = new Response();
        if (resultObject == null) {
            return resp;
        }
        try {
            FileWrapper fileWrapper = (FileWrapper) resultObject;
            if (fileWrapper.getFileSize() > 0) {
                resp.addHeader((String) HttpHeaderName.ContentLength, fileWrapper.getFileSize()
                                                                      + "");
            }
            String userAgent = request.getHeaders().get("user-agent");
            boolean isIe = userAgent == null ? false : userAgent.indexOf("MSIE") >= 0;
            String fileName = fileWrapper.getFileName();
            if (fileName != null) {
                fileName = new String(fileName.getBytes(isIe ? "GBK" : "UTF-8"), "iso8859_1");
                resp.addHeader(HttpHeaderName.ContentDisposition, "attachment;filename=" + fileName);
            }
            String contentType = fileWrapper.getContentType() == null ? "application/octet-stream"
                : fileWrapper.getContentType();
            resp.addHeader(HttpHeaderName.ContentType, contentType);
            resp.setInputStream(fileWrapper.getFileContent());
            return resp;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //never mind
        }
        return null;
    }

    @Override
    public Response renderException(Throwable exception, Request request) {
        throw new RuntimeException(exception);
    }

    /**
     * <h1>文件封装对象</h1>
     * fileName,fileContent,fileSize<br />
     * fileSize 可不设置
     */
    public static class FileWrapper {
        private String      fileName;
        private InputStream fileContent;
        private long        fileSize;
        private String      contentType;

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        /**
         * @return the fileContent
         */
        public InputStream getFileContent() {
            return fileContent;
        }

        /**
         * @param fileContent the fileContent to set
         */
        public void setFileContent(InputStream fileContent) {
            this.fileContent = fileContent;
        }

    }
}
