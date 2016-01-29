package com.netfinworks.rest.filter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

import com.netfinworks.rest.annotation.WebResource;
import com.netfinworks.rest.annotation.WebResourceAnnotationData;
import com.netfinworks.rest.server.DefaultRestServer;
import com.netfinworks.rest.server.IRestServer;
import com.netfinworks.rest.util.AnnotationUtil;
import com.netfinworks.rest.util.HttpHeaderName;
import com.netfinworks.rest.util.Magic;

/**
 * Rest Filter
 * 
 * @author bigknife
 * 
 */
public class RestFilter implements Filter {
    private Logger              logger                 = null;
    private static final String defaultConfigLocation  = "classpath*:META-INF/rest/restApplicationContext.xml";
    private static final String configLocationSplitter = "[,]";
    private static final String logbackConfiguration   = "logbackConfiguration";
    private static final String serverInfoParamName    = "serverInfo";
    private static final String startDelayParamName    = "startDelay";
    private static final String noFilterUriParamName   = "noFilterUri";
    private IRestServer         restServer;
    private Pattern             noFilterUri;
    private static String       serverInfo;

    /**
     * 初始化Filter
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        // 延时启动
        long startDelay = 0;
        try {
            String startDelayParam = filterConfig.getInitParameter(startDelayParamName);
            if (startDelayParam != null) {
                startDelay = Long.parseLong(filterConfig.getInitParameter(startDelayParamName));
            }
            System.out.println("start delay" + startDelay + " 毫秒");
            Thread.sleep(startDelay);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // init logger
        String logbackConf = filterConfig.getInitParameter(logbackConfiguration);

        if (logbackConf != null) {
            // 设置Log日志配置文件
            try {
                initLogback(logbackConf);
            } catch (JoranException e) {
                throw new ServletException(e);
            }

        } else {
            logger = LoggerFactory.getLogger(getClass());
            logger.warn("请指定logback配置文件路径，否则采用logback默认的配置加载方式。");
        }

        // 不经过滤的内容
        String noFilterUriStr = filterConfig.getInitParameter(noFilterUriParamName);
        if (noFilterUriStr != null && noFilterUriStr.trim().length() > 0) {
            noFilterUri = Pattern.compile(noFilterUriStr);
        }

        serverInfo = filterConfig.getInitParameter(serverInfoParamName);
        serverInfo = (serverInfo == null || serverInfo.trim().length() == 0) ? Magic.RestServerInfo : serverInfo;

        this.logger.info("Starting {} ...", serverInfo);
        initApplicationContext(filterConfig.getInitParameter(Magic.ConfigLocations));
        this.restServer = createRestServer(filterConfig);
        if ((this.restServer instanceof ApplicationContextAware)) {
            ((ApplicationContextAware) this.restServer).setApplicationContext(AppContextHolder.getInstanse()
                .getAppContext());
        }

        registerWebResource();

        this.restServer.init();
        this.logger.info("{} started.", serverInfo);
    }

    private void initLogback(String logbackConfigFile) throws JoranException {

        File file = new File(logbackConfigFile);
        if (!file.exists() || file.isDirectory()) {
            logger = LoggerFactory.getLogger(getClass());
            logger.error("logback配置文件不存在:" + logbackConfigFile);
        } else {
            JoranConfigurator configurator = new JoranConfigurator();
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

            loggerContext.reset();
            configurator.setContext(loggerContext);
            configurator.doConfigure(file);
            StatusPrinter.print(loggerContext);
            logger = LoggerFactory.getLogger(getClass());
            logger.info("logback配置文件指定为：{}", new String[] { logbackConfigFile });
        }

    }

    /**
     * do Filter
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                             ServletException {
        if (noFilterUri != null) {
            String requestURI = ((HttpServletRequest) request).getRequestURI();
            if (noFilterUri.matcher(requestURI).matches()) {
                logger.debug("REST Server skip uri:{}", requestURI);
                chain.doFilter(request, response);
                return;
            }
        }
        try {
            // hold request and response
            RawHttpHolder.setHttpServletRequest((HttpServletRequest) request);
            RawHttpHolder.setHttpServletResponse((HttpServletResponse) response);

            // build rest request
            Request restRequest = buildRestRequest((HttpServletRequest) request);
            // serve the http request via rest server
            Response restResponse = this.restServer.serve(restRequest);
            // send to client

            // setting server info
            restResponse.addHeader(HttpHeaderName.Server, serverInfo);

            sendResponse((HttpServletResponse) response, restResponse);
        } finally {
            // clear holded http request and response
            RawHttpHolder.clear();
        }
    }

    /**
     * 发送响应信息
     * 
     * @param response
     * @param restResponse
     * @throws IOException
     */
    private void sendResponse(HttpServletResponse response, Response restResponse) throws IOException {
        if (restResponse != null) {
            response.setStatus(restResponse.getStatus().value());

            for (String name : restResponse.getHeaderNames()) {
                response.addHeader(name, restResponse.getHeader(name));
            }
            InputStream in = restResponse.getInputStream();
            try {
                if (in != null)
                    IOUtils.copy(in, response.getOutputStream());
            } finally {
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(response.getOutputStream());
            }
        }
    }

    /**
     * 构建Rest请求对象
     * 
     * @param request
     * @return
     */
    private Request buildRestRequest(HttpServletRequest request) {
        Request restRequest = new Request();

        restRequest.setUrl(request.getRequestURI().substring(request.getContextPath().length()));

        restRequest.setQueryString(request.getQueryString());

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = (String) headers.nextElement();
            String value = request.getHeader(name);
            this.logger.debug("add header: name={},value={}", new String[] { name, value });
            restRequest.addHeader(name, value);
        }

        restRequest.setRawRequest(request);

        return restRequest;
    }

    /**
     * 销毁
     */
    public void destroy() {
        AppContextHolder.getInstanse().close();
        if (this.restServer != null)
            this.restServer.destroy();
    }

    /**
     * 初始化ApplicationContext
     * 
     * @param beansDef
     * @throws ServletException
     */
    private void initApplicationContext(String beansDef) throws ServletException {
        String[] contextResources = { defaultConfigLocation };
        if ((beansDef != null) && (beansDef.trim().length() > 0)) {
            List<String> confLocationList = new ArrayList<String>();
            String[] locations = beansDef.split(configLocationSplitter);
            for (String location : locations) {
                confLocationList.add(location.trim());
            }

            contextResources = StringUtils.toStringArray(confLocationList);
        }
        AppContextHolder.getInstanse().init(contextResources);
    }

    /**
     * 注册Web资源
     */
    private void registerWebResource() {
        Map<String, Object> allWebResBeans = AppContextHolder.getInstanse().getAppContext()
            .getBeansWithAnnotation(WebResource.class);
        for (Object bean : allWebResBeans.values()) {
            WebResourceAnnotationData wrad = AnnotationUtil.getWebResourceAnnotationData(bean);
            if (wrad != null) {
                String url = wrad.getUrl();
                this.restServer.registerWebResource(url, wrad.getUrlMatchKind(), bean);
                this.logger.debug("register web resource,url={},bean={}", new Object[] { url, bean });
            }
        }
    }

    private IRestServer createRestServer(FilterConfig filterConfig) {
        return new DefaultRestServer();
    }
}
