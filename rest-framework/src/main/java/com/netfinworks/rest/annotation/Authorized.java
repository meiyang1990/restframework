package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.annotation.Retention;

import com.netfinworks.rest.auth.BasicAuthCheck;
import com.netfinworks.rest.auth.IAuthCheck;
import com.netfinworks.rest.util.Magic;

/**
 * 标志资源需要授权方可访问
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Authorized {
	/**
	 * 授权检查class
	 * @return
	 */
	public abstract Class<? extends IAuthCheck> check() default BasicAuthCheck.class;
	
	/**
	 * 授权检查的bean，优先使用
	 * @return
	 */
	public abstract String checkRef() default Magic.EmtpyString;
	
}
