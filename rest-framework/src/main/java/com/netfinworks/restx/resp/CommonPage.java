/**
 * 
 */
package com.netfinworks.restx.resp;

import java.util.HashMap;
import java.util.Map;

/**
 * 一般页面对象
 * @author bigknife
 *
 */
public class CommonPage<T> {
	
	private String title;
	private String description;
	
	private T data;
	private Map<String, Object> ext;
	/**
	 * 添加额外数据
	 * @param name
	 * @param data
	 */
	public void addExtData(String name, Object data){
		if(ext == null){
			ext = new HashMap<String, Object>();
		}
		ext.put(name, data);
	}
	public void removeExtData(String name){
		if(ext != null){
			ext.remove(name);
		}
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}
	/**
	 * @return the ext
	 */
	public Map<String, Object> getExt() {
		return ext;
	}
	/**
	 * @param ext the ext to set
	 */
	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}
	
	
}
