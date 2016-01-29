/**
 * 
 */
package com.netfinworks.rest.template.domain;

/**
 * 应用系统接口
 * @author bigknife
 *
 */
public class Application {
	//应用名称
	private String name;
	//应用URL
	private String url;
	//是否时当前应用
	private boolean active;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
