package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.convert.IParamConvert;
import com.netfinworks.rest.convert.PrimitiveParamConvert;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.Magic;

/**
 * 标志该参数为header map
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface HeaderParam {
	/**
	 * 字符串数据编码
	 * @return
	 */
	public abstract String encoding() default Encoding.UTF_8;
	/**
	 * 参数名称
	 * @return
	 */
	public abstract String name();
	/**
	 * 参数转换器class
	 * @return
	 */
	public abstract Class<? extends IParamConvert> converter() default PrimitiveParamConvert.class;
	/**
	 * 参数转换器bean名称，如果有，则优先使用
	 */
	public abstract String converterRef() default Magic.EmtpyString;
}
