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
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.rest.exception.ResourceException;
import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

/**
 * <p>
 * velocity 碎片渲染器
 * </p>
 * 模板 + 碎片模板的方式
 * 
 * @author bigknife
 * 
 */
public class ClassPathVelocityFragmentRender extends BaseRender {
	private static final Logger logger = LoggerFactory
			.getLogger(ClassPathVelocityFragmentRender.class);
	private Properties velocityProp;
	private static final String indexFile = "/index";
	// 整体框架数据提供者
	private FrameDataProvider frameDataProvider;
	// 整体框架模板的类路径，不带扩展名
	private String frameTemplate;
	// velocity 模板文件扩展名，默认为.vm, 配置时需带上.
	private String suffix = ".vm";
	// 框架模板前缀，默认为/META-INF/velocity/frame
	private String frameTemplatePrefix = "/META-INF/velocity/frame";
	// 碎片模板前缀，默认为/META-INF/velocity/fragment
	private String fragmentTemplatePrefix = "/META-INF/velocity/fragment";
	// 碎片在frame中的变量名，默认为SCREEN_CONTENT
	private String fragmentVariableName = "SCREEN_CONTENT";
	// 数据在碎片模板里的名称，默认为data
	private String fragmentDataName = "data";
	// 数据在框架模板中的数据名称，默认为frameData
	private String frameDataName = "frameData";

	// 是否仅渲染框架
	private boolean onlyRenderFrame = false;

	/**
	 * @param onlyRenderFrame
	 *            the onlyRenderFrame to set
	 */
	public void setOnlyRenderFrame(boolean onlyRenderFrame) {
		this.onlyRenderFrame = onlyRenderFrame;
	}

	/**
	 * @param velocityProp
	 *            the velocityProp to set
	 */
	public void setVelocityProp(Properties velocityProp) {
		this.velocityProp = velocityProp;
	}

	/**
	 * @param frameTemplatePrefix
	 *            the 框架模板前缀，默认为/META-INF/velocity/frame to set
	 */
	public void setFrameTemplatePrefix(String frameTemplatePrefix) {
		this.frameTemplatePrefix = frameTemplatePrefix;
	}

	/**
	 * @param fragmentTemplatePrefix
	 *            the 碎片模板前缀，默认为/META-INF/velocity/fragment to set
	 */
	public void setFragmentTemplatePrefix(String fragmentTemplatePrefix) {
		this.fragmentTemplatePrefix = fragmentTemplatePrefix;
	}

	/**
	 * @param frameDataProvider
	 *            the 整体框架数据提供者 to set
	 */
	public void setFrameDataProvider(FrameDataProvider frameDataProvider) {
		this.frameDataProvider = frameDataProvider;
	}

	/**
	 * @param frameTemplate
	 *            the 整体框架模板的类路径，不带扩展名 to set
	 */
	public void setFrameTemplate(String frameTemplate) {
		this.frameTemplate = frameTemplate;
	}

	/**
	 * @param suffix
	 *            the velocity 模板文件扩展名，默认为.vm, 配置时需带上. to set
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * @param fragmentVariableName
	 *            the 碎片在frame中的变量名，默认为SCREEN_CONTENT to set
	 */
	public void setFragmentVariableName(String fragmentVariableName) {
		this.fragmentVariableName = fragmentVariableName;
	}

	/**
	 * @param fragmentDataName
	 *            the 数据在碎片模板里的名称，默认为data to set
	 */
	public void setFragmentDataName(String fragmentDataName) {
		this.fragmentDataName = fragmentDataName;
	}

	/**
	 * @param frameDataName
	 *            the 数据在框架模板中的数据名称，默认为frameData to set
	 */
	public void setFrameDataName(String frameDataName) {
		this.frameDataName = frameDataName;
	}

	/**
	 * velocity 初始化
	 */
	private VelocityEngine newVelocityEngine() {
		try {
			VelocityEngine ve = new VelocityEngine(velocityProp);
			Velocity.init();
			return ve;
		} catch (Exception e) {
			logger.error("Velocity 初始化异常", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Response render(Object resultObject, Request request) {
		VelocityContext globalCtx = new VelocityContext();
		// 片段html
		String frgmntHtml = onlyRenderFrame ? null : generateFragmentHtml(
				globalCtx, resultObject, request);
		// 整体模板称之为框架模板
		String frmHtml = generateFrameHtml(globalCtx, frgmntHtml,request);

		// 渲染为resp
		Response resp = new Response();
		byte[] bytes = Encoding.decode(frmHtml);
		resp.setInputStream(new ByteArrayInputStream(bytes));
		resp.addHeader(HttpHeaderName.ContentType, ContentType.Html);
		return resp;

	}

	private String generateFrameTempaltePath(Request request){
		if(onlyRenderFrame){
			//按照碎片处理方式查找
			return generateFragmentTemplatePath(request);
		}
		String frmTemplateName = frameTemplate.endsWith(suffix) ? frameTemplate
				: (frameTemplate + suffix);
		// 添加前缀
		frmTemplateName = frameTemplatePrefix + frmTemplateName;
		return frmTemplateName;
	}
	private String generateFrameHtml(VelocityContext ctxFrame,
			String fragmentHtml,Request request) {
		if (fragmentHtml != null) {
			ctxFrame.put(fragmentVariableName, fragmentHtml);
		}
		// 其他frame级别的数据用provider提供的数据
		if (this.frameDataProvider != null) {
			Object frmObj = frameDataProvider.provide();
			if (frmObj != null) {
				ctxFrame.put(frameDataName, frmObj);
			}
		}

		String frmTemplateName =  generateFrameTempaltePath(request);
		logger.debug("待渲染框架模板为：{}", frmTemplateName);

		StringWriter frmWriter = null;
		try {
			Template template = newVelocityEngine()
					.getTemplate(frmTemplateName);
			frmWriter = new StringWriter();
			template.merge(ctxFrame, frmWriter);
			String html = frmWriter.getBuffer().toString();
			if (logger.isDebugEnabled()) {
				logger.debug("生成框架{} HTML: \r\n {}", new Object[] {
						frmTemplateName, html });
			}
			return html;
		} catch (Exception e) {
			logger.error("render frame error", e);
			throw new ResourceException("render frame error");
		} finally {
			IOUtils.closeQuietly(frmWriter);
		}
	}

	private String generateFragmentTemplatePath(Request request){
		String urlTemplate = request.getUrlTemplate();
		String frgmntTemplateName = Magic.Slash.equals(urlTemplate) ? indexFile
				: (urlTemplate.endsWith(suffix) ? urlTemplate.substring(0,
						urlTemplate.indexOf(suffix)) : urlTemplate);
		// 添加后缀
		frgmntTemplateName += suffix;
		// 添加前缀
		frgmntTemplateName = (onlyRenderFrame ? frameTemplatePrefix : fragmentTemplatePrefix) + frgmntTemplateName;
		return frgmntTemplateName;
	}
	// 生成片段html
	private String generateFragmentHtml(VelocityContext ctxFrgmnt,
			Object resultObject, Request request) {
		// 渲染碎片为一个string
		if (resultObject != null) {
			ctxFrgmnt.put(fragmentDataName, resultObject);
		}
		// 其他frame级别的数据用provider提供的数据
		if (this.frameDataProvider != null) {
			Object frmObj = frameDataProvider.provide();
			if (frmObj != null) {
				ctxFrgmnt.put(frameDataName, frmObj);
			}
		}

		// 碎片模板
		String frgmntTemplateName = generateFragmentTemplatePath(request);

		logger.debug("待渲染碎片模板为：{}", frgmntTemplateName);

		StringWriter frgmntWriter = null;
		try {
			Template template = newVelocityEngine().getTemplate(
					frgmntTemplateName);
			frgmntWriter = new StringWriter();

			template.merge(ctxFrgmnt, frgmntWriter);

			Object data = template.getData();
			logger.debug("{}", data);

			String html = frgmntWriter.getBuffer().toString();
			if (logger.isDebugEnabled()) {
				logger.debug("生成碎片{} HTML: \r\n {}", new Object[] {
						frgmntTemplateName, html });
			}
			return html;
		} catch (Exception e) {
			logger.error("渲染碎片出错", e);
			throw new ResourceException("render fragment error");
		} finally {
			IOUtils.closeQuietly(frgmntWriter);
		}
	}
}
