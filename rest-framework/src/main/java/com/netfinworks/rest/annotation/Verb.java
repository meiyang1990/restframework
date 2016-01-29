/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.enums.HttpVerb;

/**
 * Http verb annotation
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Verb {
	public abstract HttpVerb value() default HttpVerb.GET;
}
