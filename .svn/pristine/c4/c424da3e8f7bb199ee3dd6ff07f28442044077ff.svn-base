/**
 * 
 */
package com.netfinworks.rest.convert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.util.ConvertUtil;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.FormDecoder;
import com.netfinworks.rest.util.Magic;

/**
 * Form表单转换为Pojo对象
 * @author bigknife
 *
 */
public class Form2PojoParamConvert implements IParamConvert {
    private Logger                         logger                    = LoggerFactory.getLogger(getClass());
    protected static PrimitiveParamConvert primitiveConvert          = new PrimitiveParamConvert();
    protected static final Class<?>[]      emptyClazzArray           = new Class<?>[] {};
    protected String                       encoding                  = Encoding.UTF_8;
    private BeanUtilsBean                  beanUtilsBean             = new BeanUtilsBean();
    protected Map<String, IParamConvert>   classParamConvertRegistry = new HashMap<String, IParamConvert>();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public <T> T convert(String raw, Class<T> distClass) {
        Map<String, String[]> decoded = FormDecoder.decode(raw);

        try {
            T pojo = createInstance(distClass);

            Set<Entry<String, String[]>> entries = decoded.entrySet();
            for (Entry<String, String[]> entry : entries) {
                String[] values = entry.getValue();
                String name = entry.getKey();
                if (name == null || Magic.EmtpyString.equals(name)) {
                    continue;
                }
                if (distClass.isAssignableFrom(Map.class)) {
                    ((Map) pojo).put(name,
                        ConvertUtil.convertUrlEncodedStringAsArray(values, encoding, String.class, primitiveConvert));
                    continue;
                }
                Class<?> cls = getPropertyClass(distClass, name);
                if (cls == null) {
                    continue;
                }
                IParamConvert paramConvert = classParamConvertRegistry.get(cls.getName());
                Object convertedValue = ConvertUtil.convertUrlEncodedStringAsArray(values, encoding, cls,
                    paramConvert == null ? primitiveConvert : paramConvert);
                if (convertedValue != null) {
                    beanUtilsBean.setProperty(pojo, name, convertedValue);
                }
            }
            return pojo;
        } catch (InstantiationException e) {
            logger.error("can't instantiate a pojo of {}, please check the Pojo", distClass);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error("can't invoke none-args constructor of a pojo of {}, please check the Pojo", distClass);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("set pojo property error!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param distClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected <T> T createInstance(Class<T> distClass) throws InstantiationException, IllegalAccessException {
        if (distClass.isInterface()) {
            if (distClass == Map.class) {
                return (T) new HashMap();
            } else if (distClass == List.class) {
                return (T) new ArrayList();
            }
            throw new InstantiationException("Cant instance class:" + distClass);
        } else {
            return distClass.newInstance();
        }
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * 设置日期转换类型，默认为yyyy-MM-dd HH:mm:ss
     * @param dateFormat
     */
    public void setDateFormat(String dateFormat) {
        primitiveConvert.setDateFormat(dateFormat);
    }

    /**
     * 设置自定义类型转换器注册表
     * @param classParamConvertRegistry
     */
    public void setClassParamConvertRegistry(Map<String, IParamConvert> classParamConvertRegistry) {
        this.classParamConvertRegistry = classParamConvertRegistry;
    }

    protected Class<?> getPropertyClass(Class<?> clazz, String propertyName) {
        Class<?> cls = getPropertyClass(clazz, propertyName, "get");
        if (cls == null) {
            cls = getPropertyClass(clazz, propertyName, "is");
        }
        return cls;

    }

    private Class<?> getPropertyClass(Class<?> clazz, String propertyName, String prefix) {
        StringBuffer buf = new StringBuffer(propertyName);
        int char0 = buf.charAt(0);
        buf.setCharAt(0, (char) (char0 >= 97 ? char0 - 32 : char0));
        buf.insert(0, prefix);
        Method m;
        try {
            m = clazz.getMethod(buf.toString(), emptyClazzArray);
            return m.getReturnType();
        } catch (SecurityException e) {
            logger.debug("property can't be access! ");
            return null;
        } catch (NoSuchMethodException e) {
            logger.debug("property '{}' doesn't exist! ", buf.toString());
            return null;
        }

    }

}
