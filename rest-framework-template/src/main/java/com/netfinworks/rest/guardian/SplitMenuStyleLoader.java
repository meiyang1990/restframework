/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
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
 * <p>App切换部分是一级菜单，右边栏是二级菜单的模式</p>
 * @author huipeng
 * @version $Id: SplitMenuStyleLoader.java, v 0.1 Apr 16, 2014 9:58:21 AM knico Exp $
 */
public class SplitMenuStyleLoader implements ApplicationsLoader, ModulesLoader {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /* (non-Javadoc)
     * @see com.netfinworks.rest.template.util.ModulesDataProvider.ModulesLoader#loadModules()
     */
    @Override
    public List<Module> loadModules() {
        List<Module> ret = null;
        String topMenu = InteractiveHelper.getTopMenuCode();
        if (topMenu != null) {
            ret = loadSubMenuRemote(topMenu);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see com.netfinworks.rest.template.util.ApplicationsDataProvider.ApplicationsLoader#loadApplications()
     */
    @Override
    public List<Application> loadApplications() {
        return this.loadTopLevelMenusRemote();
    }

    /**
     * @param topMenu 
     * @return
     */
    private List<Module> loadSubMenuRemote(String topMenu) {
        List<Module> ret = new ArrayList<Module>();
        try {
            List<Menu> menus = MenuUtil.getSubByMenuCode(topMenu, SubMenuTypeKind.MENU,
                RawHttpHolder.getServletRequest(), MenuFromKind.self);
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
    private List<Application> loadTopLevelMenusRemote() {
        List<Application> ret = new ArrayList<Application>();
        try {
            List<Menu> menus = MenuUtil.getSubByMenuCode("_MODULES_ROOT", SubMenuTypeKind.MENU,
                RawHttpHolder.getServletRequest(), MenuFromKind.self);
            if (menus != null) {
                for (Menu menu : menus) {
                    Application app = new Application();
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
