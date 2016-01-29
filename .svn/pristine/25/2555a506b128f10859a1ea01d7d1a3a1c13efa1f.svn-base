/*
 * Copyright 2012 weibopay.com, Inc. All rights reserved.
 * weibopay.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.netfinworks.rest.convert;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.util.ConvertUtil;
import com.netfinworks.rest.util.Magic;

/**
 * <p>处理multipartform。主要处理文件上传</p>
 * @author zhangjiewen
 * @version $Id: FileParamConvert.java, v 0.1 2012-11-15 下午6:09:32 zhangjiewen Exp $
 */
public class MultiPartFormDataParamConvert extends Form2PojoParamConvert implements DisposableBean {
    private Logger                     logger  = LoggerFactory.getLogger(getClass());
    private static FileCleaningTracker tracker = new FileCleaningTracker();
    private static DiskFileItemFactory factory = new DiskFileItemFactory();
    static {
        factory.setSizeThreshold(Magic.MultiPartMaxMemorySize);
        //factory.setRepository(yourTempDirectory);
        factory.setFileCleaningTracker(tracker);
    }

    public void destroy() throws Exception {
        logger.info("MultiPartFormDataParamConvert destroy.");
        tracker.exitWhenFinished();
    }

    @Override
    public <T> T convert(String paramString, Class<T> paramClass) {
        throw new IllegalAccessError("multipart form can't convert by string.");
    }

    @SuppressWarnings("rawtypes")
    public <T> T convert(Request restRequest, Class<T> paramClass) {
        //最为multipart/form-data表单处理为对象，request中的文件使用String/inputStream放到对象中        
        T pojo;
        try {
            pojo = paramClass.newInstance();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(Magic.MultiPartFileSizeMax);
            List items = upload.parseRequest(restRequest.getRawRequest());
            Iterator iter = items.iterator();//upload.getItemIterator(restRequest.getRawRequest());
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                logger.debug(item.toString());
                if (item.isFormField()) {
                    processFormField(item, pojo);
                } else {
                    processUploadedFile(item, pojo);
                }
            }
            return pojo;
        } catch (InstantiationException e) {
            logger.error("can't instantiate a pojo of {}, please check the Pojo", paramClass);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(
                "can't invoke none-args constructor of a pojo of {}, please check the Pojo",
                paramClass);
            throw new RuntimeException(e);
        } catch (FileUploadException e) {
            logger.error("fileupload error.", paramClass);
            throw new RuntimeException(e);
        }
    }

    public <T> void processUploadedFile(FileItem item, T pojo) {
        try {
            String name = item.getFieldName();
            if (name == null || Magic.EmtpyString.equals(name)) {
                return;
            }
            Class<?> cls = getPropertyClass(pojo.getClass(), name);
            if (cls == null) {
                return;
            }
            if (cls.equals(String.class)) {
                String value = item.getString(encoding);
                IParamConvert paramConvert = classParamConvertRegistry.get(cls.getName());
                Object oldValue = getPropertyValue(pojo, pojo.getClass(), name);
                Object convertedValue = ConvertUtil.addUrlEncodedStringIfArray(oldValue, value,
                    encoding, cls, paramConvert == null ? primitiveConvert : paramConvert);
                if (convertedValue != null) {
                    BeanUtils.setProperty(pojo, name, convertedValue);
                }
            } else if (cls.equals(InputStream.class)) {
                Object convertedValue = item.getInputStream();
                if (convertedValue != null) {
                    BeanUtils.setProperty(pojo, name, convertedValue);
                }
            } else if (cls.equals(FileItem.class)) {
                BeanUtils.setProperty(pojo, name, item);
            } else {
                logger.warn("Not support field[{}] type:{}.", name, cls);
            }
        } catch (IllegalAccessException e) {
            logger.error(
                "can't invoke none-args constructor of a pojo of {}, please check the Pojo",
                pojo.getClass());
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("set pojo property error!", e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            logger.error("can't convert stream to " + encoding, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error("getInputStream from item error!", e);
            throw new RuntimeException(e);
        }
    }

    public <T> void processFormField(FileItem item, T pojo) {
        try {
            String value = item.getString(encoding);
            String name = item.getFieldName();
            if (name == null || Magic.EmtpyString.equals(name)) {
                return;
            }
            Class<?> cls = getPropertyClass(pojo.getClass(), name);
            if (cls == null) {
                return;
            }
            IParamConvert paramConvert = classParamConvertRegistry.get(cls.getName());
            Object oldValue = getPropertyValue(pojo, pojo.getClass(), name);
            Object convertedValue = ConvertUtil.addUrlEncodedStringIfArray(oldValue, value,
                encoding, cls, paramConvert == null ? primitiveConvert : paramConvert);
            if (convertedValue != null) {
                BeanUtils.setProperty(pojo, name, convertedValue);
            }

        } catch (IllegalAccessException e) {
            logger.error(
                "can't invoke none-args constructor of a pojo of {}, please check the Pojo",
                pojo.getClass());
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("set pojo property error!", e);
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            logger.error("can't convert stream to " + encoding, e);
            throw new RuntimeException(e);
        }
    }

    protected <T> Object getPropertyValue(T pojo, Class<?> clazz, String propertyName) {
        Object v = null;
        try {
            v = getPropertyValue(pojo, clazz, propertyName, "get");
        } catch (NoSuchMethodException e) {
            try {
                v = getPropertyValue(pojo, clazz, propertyName, "is");
            } catch (NoSuchMethodException e1) {
                logger.debug("property '{}' doesn't exist! ", propertyName);
            }
        }
        return v;

    }

    private <T> Object getPropertyValue(T pojo, Class<?> clazz, String propertyName, String prefix)
                                                                                                   throws NoSuchMethodException {
        StringBuffer buf = new StringBuffer(propertyName);
        int char0 = buf.charAt(0);
        buf.setCharAt(0, (char) (char0 >= 97 ? char0 - 32 : char0));
        buf.insert(0, prefix);
        Method m = null;
        try {
            m = clazz.getMethod(buf.toString(), emptyClazzArray);
            return m.invoke(pojo, new Object[0]);
        } catch (SecurityException e) {
            logger.debug("property can't be access! ");
            return null;
        } catch (NoSuchMethodException e) {
            logger.debug("property '{}' doesn't exist! ", buf.toString());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.debug("method can't be invoke! wrong argument. " + e.toString());
            return null;
        } catch (IllegalAccessException e) {
            logger.debug("method can't be invoke! access exception. " + e.toString());
            return null;
        } catch (InvocationTargetException e) {
            logger.debug("method can't be invoke! invocation target. " + e.toString());
            return null;
        }

    }
}
