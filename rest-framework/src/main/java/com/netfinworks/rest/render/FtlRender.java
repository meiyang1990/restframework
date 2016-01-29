/**
 * 
 */
package com.netfinworks.rest.render;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.netfinworks.rest.filter.Request;
import com.netfinworks.rest.filter.Response;
import com.netfinworks.rest.util.ContentType;
import com.netfinworks.rest.util.Encoding;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author bigknife
 *
 */
public class FtlRender implements IRender {
	private static Logger logger = LoggerFactory.getLogger(FtlRender.class);
	private static Configuration conf = new Configuration();
	static {
		conf.setOutputEncoding(Encoding.UTF_8);
        conf.setDefaultEncoding(Encoding.UTF_8);
		conf.setClassForTemplateLoading(FtlRender.class, "/META-INF/html/");
		
		try{
			ClassPathResource res = new ClassPathResource("/META-INF/prop/freemarker.properties");
			conf.setSettings(res.getInputStream());
		}catch(Exception ex){
			logger.warn("freemarker setting file error!  please check /META-INF/prop/freemarker.properties!");
		}
	}
	
	private String suffix = ".html";
	private String indexFile = "index";
	
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




	@Override
	public Response render(Object resultObject, Request request) {
		String urlTemplate = request.getUrlTemplate();
		StringWriter writer = null;
		try {
			Template t = conf
					.getTemplate(urlTemplate.endsWith(suffix) ? urlTemplate
							: (Magic.Slash.equals(urlTemplate) ? indexFile
									: urlTemplate)
									+ suffix);
			writer = new StringWriter();
			t.process(resultObject, writer);
			String html = writer.getBuffer().toString();
			byte [] bytes = Encoding.decode(html);
			Response resp = new Response();
			resp.setInputStream(new ByteArrayInputStream(bytes));
			resp.addHeader(HttpHeaderName.ContentType, ContentType.Html);
			return resp;
		} catch (IOException e) {
			logger.error("can't find template of {}", urlTemplate);
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			logger.error("freemarker template parsing error!");
			throw new RuntimeException(e);
		}finally{
			IOUtils.closeQuietly(writer);
		}
	}
	
	@Override
	public Response renderException(Throwable exception, Request request) {
		throw new IllegalAccessError("un implemented method!");
	}

}
