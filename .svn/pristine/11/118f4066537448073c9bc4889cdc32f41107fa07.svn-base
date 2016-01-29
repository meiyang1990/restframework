/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

/**
 * <p>对应velocity的模板，将resultObject为键放到context中使用</p>
 * @author zhangjiewen
 * @version $Id: VelocityRender.java, v 0.1 2012-9-18 下午5:24:13 zhangjiewen Exp $
 */
public class VelocityRender implements IRender,InitializingBean{
    public static final String CONTEXT_KEY = "resultObject";
    
    private Logger         logger         = LoggerFactory.getLogger(VelocityRender.class);    
    private static final String   suffix         = ".html";
    private static final String   prefix         =".html";
    private static final String   indexFile      = "index";
    private String   propFile       = "META-INF/velocity/velocity.properties";
    private Properties properties = null;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
    private void init() {
        try {
            logger.info("VelocityRender init begin.");
            if(properties == null ){
                Resource resource = new ClassPathResource(propFile);
                Velocity.init(resource.getFile().getAbsolutePath());
            }else{
                String filePath = properties.getProperty("file.resource.loader.path");
                if(filePath != null){
                    Resource resource = new ClassPathResource(filePath);
                    String filePathToUse = resource.getFile().getAbsolutePath();
                    properties.put("file.resource.loader.path", filePathToUse);
                }
                Velocity.init(properties);
            }
            logger.info("VelocityRender init end.");
        } catch (Exception e) {
            logger.error("velocityEngine init error ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response render(Object resultObject, Request request) {
        
        String urlTemplate = request.getUrlTemplate();
        String templateName = Magic.Slash.equals(urlTemplate) ? indexFile
            : (urlTemplate.endsWith(suffix) ? urlTemplate.substring(0, urlTemplate.indexOf(suffix))
                : urlTemplate);
        templateName += prefix;
        logger.info("render begin.templateName is {}",templateName);
        StringWriter writer = null;
        try {            
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put(CONTEXT_KEY, resultObject);
            Template template = Velocity.getTemplate(templateName); 
            writer = new StringWriter();
            template.merge(velocityContext, writer);              
            String html = writer.getBuffer().toString();
            byte [] bytes = Encoding.decode(html);
            Response resp = new Response();
            resp.setInputStream(new ByteArrayInputStream(bytes));
            resp.addHeader(HttpHeaderName.ContentType, ContentType.Html);
            return resp;
        } catch (Exception e) {
            logger.error("velocityEngine process error ", e);
            throw new RuntimeException(e);
        }finally{
            IOUtils.closeQuietly(writer);
        }
    }

    @Override
    public Response renderException(Throwable exception, Request request) {
        throw new IllegalAccessError("un implemented method!");
    }

    public void setPropFile(String propFile) {
        this.propFile = propFile;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
