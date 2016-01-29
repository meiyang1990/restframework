/**
 * 
 */
package com.netfinworks.rest.template.util;

import java.util.List;

import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.rest.template.domain.Application;

/**
 * 应用系统数据加载器
 * @author bigknife
 *
 */
public class ApplicationsDataProvider implements FrameDataProvider{
	public static interface ApplicationsLoader{
		//加载所有的应用系统
		List<Application> loadApplications();
	}
	private ApplicationsLoader applicationsLoader;
	@Override
	public Object provide() {
		if(applicationsLoader != null){
			return applicationsLoader.loadApplications();
		}
		return null;
	}
	/**
	 * @param applicationsLoader the applicationsLoader to set
	 */
	public void setApplicationsLoader(ApplicationsLoader applicationsLoader) {
		this.applicationsLoader = applicationsLoader;
	}
	
}
