/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.render.DefaultRender;
import com.netfinworks.rest.render.IRender;
import com.netfinworks.rest.util.Magic;

/**
 * render
 * 
 * @author bigknife
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Render {
	/**
	 * 渲染器Class，运行时动态实例化IRender对象执行渲染response操作
	 * 
	 * @return
	 */
	public abstract Class<? extends IRender> render() default DefaultRender.class;

	/**
	 * 渲染器bean名称，如果指定了renderRef参数，则优先从context中获取该render，而忽略render属性指定的class
	 * 
	 * @see {@link Render#render()}
	 * @return
	 */
	public abstract String renderRef() default Magic.EmtpyString;

	/**
	 * 异常渲染器Class，运行时动态实例化IRender对象执行渲染response操作
	 * 
	 * @return
	 */
	public abstract Class<? extends IRender> exceptionRender() default DefaultRender.class;

	/**
	 * 异常渲染器bean名称，如果指定了exceptionRenderRef参数，则优先从context中获取该render，
	 * 而忽略exceptionRender属性指定的class
	 * 
	 * @see {@link Render#exceptionRender()}
	 * @return
	 */
	public abstract String exceptionRenderRef() default Magic.EmtpyString;
}
