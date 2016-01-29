/**
 * 
 */
package com.netfinworks.rest.guardian;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netfinworks.guardian.common.GuardianEvent;
import com.netfinworks.guardian.sso.filter.listener.BaseGuardianSSOFilterListener;
import com.netfinworks.guardian.sso.filter.listener.DefaultListener;
import com.netfinworks.rest.template.util.JsonUtil;

/**
 * unilog admin 的sso filter 监听器
 * 
 * @author bigknife
 * 
 */
public class TemplateSSOListener extends BaseGuardianSSOFilterListener {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final String reloginBridgeUrl = "/relogin-bridge";
	private final String reloginUrl = "relogin";
	private final String forbiddenInlineUrl = "/forbidden-inline";
	private final String forbiddenUrl = "/forbidden";

	private DefaultListener defaultListener = new DefaultListener();

	@Override
	public void handleNotLoginEvent(GuardianEvent event) {
		HttpServletRequest request = event.getRequest();
		HttpServletResponse response = event.getResponse();

		//如果是ajax 则返回一个
		if (isAjaxRequest(request)) {
			if (isModuleRequest(request) || isDialogRequest(request)) {
				redirectToBridge(request, response);
			} else {
				//data request
				writeRedirectResponse(response);
			}
		} else {
			//不是ajax 则直接重定向到登录页面
			defaultListener.handleNotLoginEvent(event);
		}
	}

	@Override
	public void handleForbiddenEvent(GuardianEvent event) {
		HttpServletRequest request = event.getRequest();
		HttpServletResponse response = event.getResponse();

		//如果是ajax 则返回一个
		if (isAjaxRequest(request)) {
			if (isModuleRequest(request) || isDialogRequest(request)) {
				redirectToForidenInline(request, response);
			} else {
				//data request
				writeForbiddenResponse(response);
			}
		} else {
			//不是ajax 则直接重定向到禁止页面
			redirectToForiden(request, response);
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void redirectToForiden(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + forbiddenUrl);
		} catch (IOException e) {
			logger.error("重定向到禁止页面出错", e);
		}
	}

	/**
	 * @param request
	 * @param response
	 */
	private void redirectToForidenInline(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + forbiddenInlineUrl);
		} catch (IOException e) {
			logger.error("重定向到内含禁止页面出错", e);
		}
	}

	private void writeRedirectResponse(HttpServletResponse response) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("filterFoundNotLogin", true);
		resp.put("redirect", reloginUrl);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(JsonUtil.serialize(resp));
		} catch (IOException e) {
			logger.error("输出Json 重定向消息出错", e);
		}
	}

	private void writeForbiddenResponse(HttpServletResponse response) {
		Map<String, Object> resp = new HashMap<String, Object>();
		resp.put("filterFoundForbidden", true);
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(JsonUtil.serialize(resp));
		} catch (IOException e) {
			logger.error("输出Json禁止消息出错", e);
		}
	}

	private void redirectToBridge(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + reloginBridgeUrl);
		} catch (IOException e) {
			logger.error("重定向到桥接页面出错", e);
		}
	}

	private boolean isDialogRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		return "ria-dlg".equals(accept);
	}

	private boolean isModuleRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		return "ria-module".equals(accept);
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestWith = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestWith);
	}
}
