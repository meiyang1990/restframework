/**
 * 
 */
package com.netfinworks.restx.render;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.render.FrameDataProvider;
import com.netfinworks.rest.render.IRender;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 片段式ftl 页面组装渲染器。<br />
 * 0.1-20121216-SNAPSHOT 之后数据定义有变，不兼容0.1-20121203-SNAPSHOT。<br />
 * 从  0.1-20121216-SNAPSHOT 版本后，页面数据从一个root 对象开始，框架的数据名称默认为frameData, 片段数据名称
 * 默认为fragmentData
 * @author bigknife
 * 
 */
public class FtlFragmentRender implements IRender {
	private static Logger logger = LoggerFactory.getLogger(FtlFragmentRender.class);
	private static Configuration conf = new Configuration();
	static {
		conf.setOutputEncoding(Encoding.UTF_8);
        conf.setDefaultEncoding(Encoding.UTF_8);
		conf.setClassForTemplateLoading(FtlFragmentRender.class,
				"/META-INF/html/");
		try{
			ClassPathResource res = new ClassPathResource("/META-INF/prop/freemarker.properties");
			conf.setSettings(res.getInputStream());
		}catch(Exception ex){
			logger.warn("freemarker setting file error!  please check /META-INF/prop/freemarker.properties!");
		}
		
	}

	private String suffix = ".html";
	private String indexFile = "index";
	// 框架页面
	private String frame;
	// frame data name
	private String frameDataName = "frameData";
	// fragment 在frame中的变量名称
	private String fragmentVarName = "fragment";

    public String getFrame() {
        return frame;
    }

    public String getFragmentVarName() {
        return fragmentVarName;
    }

    // fragment 数据在frame 重的变量名称
    private String fragmentDataName = "fragmentData";
	//frame data provider
	private FrameDataProvider frameDataProvider;

	public String getFrameDataName() {
        return frameDataName;
    }

    public String getFragmentDataName() {
        return fragmentDataName;
    }

    public FrameDataProvider getFrameDataProvider() {
        return frameDataProvider;
    }

    /**
	 * @param frameDataProvider the frameDataProvider to set
	 */
	public void setFrameDataProvider(FrameDataProvider frameDataProvider) {
		this.frameDataProvider = frameDataProvider;
	}

    public void setFragmentDataName(String fragmentDataName) {
        this.fragmentDataName = fragmentDataName;
    }

    /**
	 * @param frameDataName the frameDataName to set
	 */
	public void setFrameDataName(String frameDataName) {
		this.frameDataName = frameDataName;
	}

	/**
	 * @param fragmentVarName
	 *            the fragmentVarName to set
	 */
	public void setFragmentVarName(String fragmentVarName) {
		this.fragmentVarName = fragmentVarName;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	/**
	 * @param suffix
	 *            the suffix to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}

	@Override
	public Response render(Object resultObject, Request request) {
        //整个页面的数据对象
        Map<String , Object> rootObj = new HashMap<String, Object>(3);
        rootObj.put("enums", BeansWrapper.getDefaultInstance().getEnumModels());
        rootObj.put("static", BeansWrapper.getDefaultInstance().getStaticModels());
        Object frameDataObj = frameDataProvider.provide();
        rootObj.put(frameDataName,frameDataObj);
        rootObj.put(fragmentDataName,generateResultObject(resultObject, request));
        //渲染片段
		String fragmentHtml = renderFragment(rootObj, generateTemplate(resultObject, request));
        rootObj.put(fragmentVarName,fragmentHtml);
		String html = renderFrame(rootObj, frame);
        rootObj.clear();
		byte[] bytes = Encoding.decode(html);
		Response resp = new Response();
		resp.setInputStream(new ByteArrayInputStream(bytes));
		resp.addHeader(HttpHeaderName.ContentType, ContentType.Html);
		return resp;
	}
	
	protected Object generateResultObject(Object resultObject, Request request){
	    return resultObject;
	}
	
	protected String generateTemplate(Object resultObject, Request request){
	    String urlTemplate = request.getUrlTemplate();
        String fragmentTemplate = urlTemplate.endsWith(suffix) ? urlTemplate
                : (Magic.Slash.equals(urlTemplate) ? indexFile : urlTemplate)
                        + suffix;
        return fragmentTemplate;
	}


	protected String renderFrame(Map<String, Object> data, String frameTemplate) {
		StringWriter writer = new StringWriter();
		try {
			Template t = conf.getTemplate(frame,Encoding.UTF_8);
			t.process(data, writer);
			String html = writer.getBuffer().toString();
			return html;
		} catch (IOException e) {
			logger.error("can't find template of {}", frame);
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			logger.error("freemarker template parsing error!");
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	/**
	 * 渲染碎片
	 * 
	 * @param resultObject
	 * @param fragmentTemplate
	 * @return
	 */
	protected String renderFragment(Object resultObject, String fragmentTemplate) {
		StringWriter writer = new StringWriter();
		try {
			Template t = conf.getTemplate(fragmentTemplate,Encoding.UTF_8);
			t.process(resultObject, writer);
			String html = writer.getBuffer().toString();
			return html;
		} catch (TemplateException e) {
			logger.error("template[{}] parse exception : {}", new Object[]{fragmentTemplate,e.getMessage()});
			throw new RuntimeException(e);
		} catch (IOException e) {
			logger.error("freemarker template parsing error!");
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(writer);
		}

	}

	@Override
	public Response renderException(Throwable exception, Request request) {
		throw new IllegalAccessError("un implemented method!");
	}

}
