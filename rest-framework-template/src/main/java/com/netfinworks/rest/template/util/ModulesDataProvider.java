/**
 * 
 */
package com.netfinworks.rest.template.util;

import java.util.List;

import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.rest.template.domain.Module;

/**
 * 应用模板数据加载器，每个应用有一个flat的模块列表
 * @author bigknife
 *
 */
public class ModulesDataProvider implements FrameDataProvider{
	public static interface ModulesLoader{
		//加载所有的应用系统
		List<Module> loadModules();
	}
	private ModulesLoader modulesLoader;
	@Override
	public Object provide() {
		if(modulesLoader != null){
			return modulesLoader.loadModules();
		}
		return null;
	}
	/**
	 * @return the modulesLoader
	 */
	public ModulesLoader getModulesLoader() {
		return modulesLoader;
	}
	/**
	 * @param modulesLoader the modulesLoader to set
	 */
	public void setModulesLoader(ModulesLoader modulesLoader) {
		this.modulesLoader = modulesLoader;
	}
}
