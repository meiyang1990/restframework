/**
 * 
 */
package com.netfinworks.restx.render;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.netfinworks.rest.render.FrameDataProvider;

/**
 * map 数据提供者
 * 
 * @author bigknife
 * 
 */
public class FrameMapDataProvider implements FrameDataProvider {
	private Map<String, Object> map = new ProviderDataMap<String,Object>();
	
	
	private static class ProviderDataMap<K,V> extends HashMap<K, V>{
		private static final long serialVersionUID = -4644362402831455790L;
		
		@SuppressWarnings("unchecked")
		@Override
		public V get(Object key) {
			V v = super.get(key);
			while(v instanceof FrameDataProvider){
				v = (V) ((FrameDataProvider) v).provide();
			}
			
			return v;
		}
		
	}

	/**
	 * 添加数据，覆盖相同名称的数据
	 * 
	 * @param name
	 * @param data
	 */
	public void addData(String name, Object data) {
		map.put(name, data);
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, Object> map) {
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				addData(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public Object provide() {
		return map;
	}

}
