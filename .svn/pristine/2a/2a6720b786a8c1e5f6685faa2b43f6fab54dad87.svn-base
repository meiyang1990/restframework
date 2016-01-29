/**
 * 
 */
package com.netfinworks.rest.server;

import java.lang.reflect.Method;

import com.netfinworks.rest.annotation.AcceptAnnotationData;

/**
 * @author bigknife
 * 
 */
public class ResourceMethodMeta {
	private Object resource;
	private Method method;
	private AcceptAnnotationData acceptAnnotationData = new AcceptAnnotationData();

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * @return the acceptAnnotationData
	 */
	public AcceptAnnotationData getAcceptAnnotationData() {
		return acceptAnnotationData;
	}

	/**
	 * @param acceptAnnotationData the acceptAnnotationData to set
	 */
	public void setAcceptAnnotationData(AcceptAnnotationData acceptAnnotationData) {
		this.acceptAnnotationData = acceptAnnotationData;
	}

	/**
	 * @return the resource
	 */
	public Object getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setResource(Object resource) {
		this.resource = resource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName()).append("@").append(this.hashCode());
		sb.append("{").append(this.resource.getClass().getSimpleName()).append(".").append(this.method.getName()).append("}");
		return sb.toString();
	}
}
