/**
 * 
 */
package com.netfinworks.rest.guardian;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.guardian.menu.Menu;
import com.netfinworks.guardian.menu.MenuFromKind;
import com.netfinworks.guardian.menu.MenuUtil;
import com.netfinworks.guardian.menu.SubMenuTypeKind;
import com.netfinworks.rest.filter.RawHttpHolder;
import com.netfinworks.rest.template.domain.Application;
import com.netfinworks.rest.template.domain.Module;
import com.netfinworks.rest.template.util.ApplicationsDataProvider.ApplicationsLoader;
import com.netfinworks.rest.template.util.ModulesDataProvider.ModulesLoader;

/**
 * @author knico
 * @since Jan 9, 2013
 * 
 */
public class AppAndMoudleLoader implements ApplicationsLoader, ModulesLoader {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private MenuFromKind appMenuFrom = MenuFromKind.common;
	private MenuFromKind modulemenuFrom = MenuFromKind.self;

	/**
	 * @return the appMenuFrom
	 */
	public MenuFromKind getAppMenuFrom() {
		return appMenuFrom;
	}

	/**
	 * @param appMenuFrom the appMenuFrom to set
	 */
	public void setAppMenuFrom(MenuFromKind appMenuFrom) {
		this.appMenuFrom = appMenuFrom;
	}

	/**
	 * @return the modulemenuFrom
	 */
	public MenuFromKind getModulemenuFrom() {
		return modulemenuFrom;
	}

	/**
	 * @param modulemenuFrom the modulemenuFrom to set
	 */
	public void setModulemenuFrom(MenuFromKind modulemenuFrom) {
		this.modulemenuFrom = modulemenuFrom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.netfinworks.rest.template.util.ModulesDataProvider.ModulesLoader#loadModules()
	 */
	@Override
	public List<Module> loadModules() {
		return this.loadModulesRemote();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.netfinworks.rest.template.util.ApplicationsDataProvider.ApplicationsLoader#loadApplications()
	 */
	@Override
	public List<Application> loadApplications() {
		return this.loadApplicationsRemote();
	}

	/**
	 * @return
	 */
	private List<Module> loadModulesRemote() {
		List<Module> ret = new ArrayList<Module>();
		try {
			List<Menu> menus = MenuUtil.getSubByMenuCode("_MODULES_ROOT", SubMenuTypeKind.MENU, RawHttpHolder.getServletRequest(),
					modulemenuFrom);
			if (menus != null) {
				for (Menu menu : menus) {
					Module module = new Module();
					module.setName(menu.getName());
					module.setUrl(menu.getUrl());
					ret.add(module);
				}
			}
		} catch (Exception e) {
			logger.error("get module list.", e);
		}
		return ret;
	}

	/**
	 * @return
	 */
	private List<Application> loadApplicationsRemote() {
		List<Application> ret = new ArrayList<Application>();
		try {
			List<Menu> menus = MenuUtil.getSubByMenuCode("_APPLICATONS_ROOT", SubMenuTypeKind.MENU, RawHttpHolder.getServletRequest(),
					appMenuFrom);
			if (menus != null) {
				for (Menu menu : menus) {
					Application app = new Application();
					app.setActive(true);
					app.setName(menu.getName());
					app.setUrl(menu.getUrl());
					ret.add(app);
				}
			}
		} catch (Exception e) {
			logger.error("get app list.", e);
		}
		return ret;
	}
}
