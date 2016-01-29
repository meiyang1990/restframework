/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.util.ContentType;

/**
 * the content type resource can accept 
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Accept {
	/**
	 * 指定接受的内容类型
	 * @return
	 */
	public abstract String contentType() default ContentType.FormUrlEncoded;
}
