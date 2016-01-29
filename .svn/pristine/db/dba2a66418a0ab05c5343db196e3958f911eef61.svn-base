/*
 * Copyright 2012 weibopay.com, Inc. All rights reserved.
 * weibopay.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netfinworks.rest.convert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.util.Encoding;

/**
 * <p></p>
 * @author zhangjiewen
 * @version $Id: MultiDataParamConvertTest.java, v 0.1 2012-11-16 下午2:40:37 zhangjiewen Exp $
 */
public class MultiDataParamConvertTest {
    private MultiPartFormDataParamConvert convert;
    private String id = "1234";
    private String name="name5678";
    private String fileContent = "byte array 字节数组内容";
    private String fileStr = "String content 字符串内容";
    private String fileStream = "inputstream 文件流内容";
    private String requestContent = "-----1234\r\n" +
            "Content-Disposition: form-data; name=\"fileContent\"; filename=\"fileContent.tab\"\r\n" +
            "Content-Type: text/whatever\r\n" +
            "\r\n" +
            fileContent +
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"fileStr\"; filename=\"fileStr.tab\"\r\n" +
            "\r\n" +
            fileStr +
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"fileStream\"; filename=\"fileStream.tab\"\r\n" +
            "\r\n" +
            fileStream +
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"id\"\r\n" +
            "\r\n" +
            id +
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"name\"\r\n" +
            "\r\n" +
            name+
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"arr\"\r\n" +
            "\r\n" +
            "value1"+
            "\r\n" +
            "-----1234\r\n" +
            
            "Content-Disposition: form-data; name=\"arr\"\r\n" +
            "\r\n" +
            "value2\r\n" +
            "-----1234--\r\n";
    @Before
    public void init(){
        convert = new MultiPartFormDataParamConvert();
        convert.setEncoding(Encoding.UTF_8);
    }
    @Test
    public void testConvert(){
        MultiPartFormData data = convert.convert(buildRequest(), MultiPartFormData.class);
        Assert.assertEquals(id, data.getId());
        Assert.assertEquals(name, data.getName());
        Assert.assertEquals(fileStr, data.getFileStr());
//        try {
//            Assert.assertEquals(fileContent, new String(data.getFileContent(),Encoding.UTF_8));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        InputStream is = data.getFileStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            IOUtils.copy(is, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(fileStream, out.toString());
        System.out.println(data.getArr().length);
        for(String a : data.getArr()){
            System.out.println(a);
        }
    }
    public static class MultiPartFormData{
        private String name;
        private String id;
        private String fileStr;
        private InputStream fileStream;
        private String[] arr;
        private byte[] fileContent;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getFileStr() {
            return fileStr;
        }
        public void setFileStr(String fileStr) {
            this.fileStr = fileStr;
        }
        public InputStream getFileStream() {
            return fileStream;
        }
        public void setFileStream(InputStream fileStream) {
            this.fileStream = fileStream;
        }
        public byte[] getFileContent() {
            return fileContent;
        }
        public void setFileContent(byte[] fileContent) {
            this.fileContent = fileContent;
        }
        public String[] getArr() {
            return arr;
        }
        public void setArr(String[] arr) {
            this.arr = arr;
        }
    }
    private Request buildRequest(){
        Request request = new Request();
        MockHttpServletRequest rawRequest = new MockHttpServletRequest(requestContent.getBytes(),"multipart/form-data; boundary=---1234");
        request.setRawRequest(rawRequest);        
        return request;
    }
}
