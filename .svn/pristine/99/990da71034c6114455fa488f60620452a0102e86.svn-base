/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.convert.Form2PojoParamConvert;
import com.netfinworks.rest.convert.IParamConvert;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.Magic;

/**
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface QueryString {
	/**
	 * 字符编码
	 * @return
	 */
	public abstract String encoding() default Encoding.UTF_8;
	/**
	 * 参数转换器class
	 * @return
	 */
	public abstract Class<? extends IParamConvert> converter() default Form2PojoParamConvert.class;
	/**
	 * 转换器bean名称，如果指定了该属性，则优先使用
	 * @return
	 */
	public abstract String converterRef() default Magic.EmtpyString;
}
