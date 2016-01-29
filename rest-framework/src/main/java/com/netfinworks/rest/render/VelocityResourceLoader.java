/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Velocaty 资源加载
 * 
 * @author bigknife
 * 
 */
public class VelocityResourceLoader extends ResourceLoader {
    private boolean       debug  = true;
    private static Logger logger = LoggerFactory.getLogger(VelocityResourceLoader.class);

    /**
     * @param debug the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    @Override
    public long getLastModified(Resource res) {
        return 0;
    }

    @Override
    public InputStream getResourceStream(String name) throws ResourceNotFoundException {
        logger.debug("ready to find resource:{}", name);
        if (name == null || name.length() == 0) {
            throw new ResourceNotFoundException("Need to specify a template name!");
        }
        try {
            //如果debug则每次读取最新的，否则利用classloader的缓存读，只有重启jvm才会更新
            ClassPathResource res = new ClassPathResource(name);
            if (debug) {
                logger.debug("use file input get resource.");
                return new FileInputStream(res.getFile());
            }
            return res.getInputStream();
        } catch (Exception e) {
            logger.error("load resource error", e);
            throw new ResourceNotFoundException(name + "not found!");
        }
    }

    @Override
    public void init(ExtendedProperties prop) {
        debug = prop.getBoolean("debug");
        logger.debug("is debug ? {}", debug);
    }

    @Override
    public boolean isSourceModified(Resource res) {
        return true;
    }

}
