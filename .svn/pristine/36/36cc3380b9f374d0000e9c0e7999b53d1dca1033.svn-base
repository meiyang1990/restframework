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
import com.netfinworks.rest.enums.BodyAs;
import com.netfinworks.rest.util.Magic;

/**
 * Html request body
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface Body {
	/**
	 * 字符串编码，如果处理为流时无效
	 * @return
	 */
	public abstract String encoding() default "UTF-8";
	/**
	 * body处理模式
	 * @see {@link BodyAs}
	 * @return
	 */
	public abstract BodyAs as() default BodyAs.String;
	/**
	 * 参数处理器，如果处理为流时无效
	 * @return
	 */
	public abstract Class<? extends IParamConvert> converter() default Form2PojoParamConvert.class;

	/**
	 * 参数转换器bean名称，如果有，则优先使用，如果处理为流时无效
	 */
	public abstract String converterRef() default Magic.EmtpyString;
}
