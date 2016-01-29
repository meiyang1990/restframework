/**
 * 
 */
package com.netfinworks.rest.util;

import com.netfinworks.rest.annotation.UrlParam;

/**
 * 魔数常量
 * 
 * @author bigknife
 * 
 */
public class Magic {
	/**
	 * netfinworks-Rest-Server
	 */
	public static final String RestServerInfo = "netfinworks-Rest-Server";
	/**
	 * 空字符串
	 */
	public static final String EmtpyString = "";

	/**
	 * name 字符串字面常量
	 * 
	 * @see {@link UrlParam#name()}
	 */
	public static final String Name = "name";

	/**
	 * converter 字符串字面常量
	 */
	public static final String Converter = "converter";

	/**
	 * converterRef 字符串字面常量
	 */
	public static final String ConverterRef = "converterRef";
	/**
	 * render 字符串字面常量
	 */
	public static final String Render = "render";

	/**
	 * encoding 字符串字面常量
	 */
	public static final String Encoding = "encoding";

	/**
	 * as 字符串字面常量
	 */
	public static final String As = "as";

	/**
	 * configLocations 字符串字面常量
	 */
	public static final String ConfigLocations = "configLocations";

	/**
	 * url 字符串字面常量
	 */
	public static final String Url = "url";

	/**
	 * matchKind 字符串字面常量
	 */
	public static final String MatchKind = "matchKind";

	/**
	 * contentType 字符串字面常量
	 */
	public static final String ContentType = "contentType";
	/**
	 * check 字符串字面常量
	 */
	public static final String Check = "check";

	/**
	 * checkRef 字符串字面常量
	 */
	public static final String CheckRef = "checkRef";
	
	/**
	 * renderRef 字符串字面常量
	 */
	public static final String RenderRef = "renderRef";
	/**
	 * 一个空格
	 */
	public static final String OneBlank = " ";
	
	/**
	 *  Basic 字符串字面常量
	 */
	public static final String Basic = "Basic";
	/**
	 * 冒号
	 */
	public static final String Colon = ":";
	/**
	 * Basic 授权信息 "Basic realm=\"netfinworks Authenticated\""
	 */
	public static final String BasicAuthInfo = "Basic realm=\"netfinworks Authenticated\"";
	public static final String AuditRef = "auditRef";
	public static final String Audit = "audit";
	public static final String Slash = "/";
	public static final String ExceptionRender = "exceptionRender";
	public static final String ExceptionRenderRef = "exceptionRenderRef";
	/**
	 * 处理multipart/form数据 内存中最大保存数据的大小，最大单个文件大小
	 */
	public static final int MultiPartMaxMemorySize = 512*1024;
	public static final long MultiPartFileSizeMax = 5*1024*1024;
}
