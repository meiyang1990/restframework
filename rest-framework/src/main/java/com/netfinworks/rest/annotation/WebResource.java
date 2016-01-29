/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.util.UrlMatchKind;

/**
 * Web Resource
 * 
 * @author bigknife
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WebResource {
	public abstract String url();

	public abstract UrlMatchKind matchKind() default UrlMatchKind.Greedy;
}
