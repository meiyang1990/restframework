/**
 * 
 */
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
 * 标志数据来源为url 模板参数
 * <p>
 * eg. <code>/order/{id}</code> id is a url parameter
 * </p>
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UrlParam {
	/**
	 * 字符串数据编码
	 * @return
	 */
	public abstract String encoding() default Encoding.UTF_8;
	/**
	 * 参数属性名
	 * @return
	 */
	public abstract String name();
	/**
	 * 转换器class，指定的类需提供一个public的无餐构造方法，运行时会实例化。
	 * @return
	 */
	public abstract Class<? extends IParamConvert> converter() default PrimitiveParamConvert.class;
	/**
	 * 转换器bean名称，如果指定了该属性，则优先使用
	 * @return
	 */
	public abstract String converterRef() default Magic.EmtpyString;
}
