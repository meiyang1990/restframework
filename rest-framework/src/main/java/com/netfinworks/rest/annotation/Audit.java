/**
 * 
 */
package com.netfinworks.rest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.netfinworks.rest.audit.IResourceAudit;
import com.netfinworks.rest.audit.SimpleResourceAudit;
import com.netfinworks.rest.util.Magic;

/**
 * @author bigknife
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Audit {
	/**
	 * audit class
	 * @return
	 */
	public abstract Class<? extends IResourceAudit> audit() default SimpleResourceAudit.class;
	public abstract String auditRef() default Magic.EmtpyString;
}
