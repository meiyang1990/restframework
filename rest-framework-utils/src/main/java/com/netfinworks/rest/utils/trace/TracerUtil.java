/**
 * Copyright 2013 weibopay.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils.trace;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>跟踪工具</p>
 * @author huipeng
 * @version $Id: DebugTraceUtil.java, v 0.1 Aug 9, 2013 3:16:30 PM knico Exp $
 */
public class TracerUtil {
    private static Logger                       logger         = LoggerFactory
                                                                   .getLogger("com.netfinworks.tracer");
    /**
     * 开始时间
     */
    private static ThreadLocal<Long>            localTime      = new ThreadLocal<Long>();
    /**
     * 开始时间
     */
    private static ThreadLocal<Map<Long, Long>> localPointTime = new ThreadLocal<Map<Long, Long>>();
    /**
     * Logger
     */
    private static ThreadLocal<Logger>          localLogger    = new ThreadLocal<Logger>();
    /**
     * 跟踪ID
     */
    private static ThreadLocal<Long>            localId        = new ThreadLocal<Long>();
    /**
     * 跟踪ID生成
     */
    private static long                         traceId        = 10000;
    /**
     * 
     */
    public final static int                     TRACE_DEBUG    = 0;
    public final static int                     TRACE_INFO     = 1;
    public final static int                     TRACE_WARN     = 2;
    public final static int                     TRACE_ERROR    = 3;

    public static int getTraceLevel(Logger logger) {
        if (logger.isDebugEnabled()) {
            return TRACE_DEBUG;
        } else if (logger.isInfoEnabled()) {
            return TRACE_INFO;
        } else if (logger.isWarnEnabled()) {
            return TRACE_WARN;
        } else {
            return TRACE_ERROR;
        }
    }

    /**
     * 获取跟踪号（新建）
     * @return
     */
    public static long getTraceId() {
        return traceId++;
    }

    /**
     * 开始跟踪
     * @param logger
     */
    public static void startTrace(Logger logger) {
        startTrace(logger, null);
    }

    /**
     * 开始跟踪
     * @param logger
     * @param name
     */
    public static void startTrace(Logger logger, String name) {
        cleanTracer();
        if (isTraceable(logger)) {
            localLogger.set(logger);
            localTime.set(System.currentTimeMillis());
            localId.set(getTraceId());
            traceOut(logger, "#:T-{}:{} start trace.", localId.get(), name == null ? "" : name);
        }
    }

    private static void traceOut(Logger logger, String format, Object... argArray) {
        if (logger != null) {
            switch (getTraceLevel(TracerUtil.logger)) {
                case TRACE_DEBUG:
                    logger.debug(format, argArray);
                    break;
                case TRACE_INFO:
                    logger.info(format, argArray);
                    break;
                case TRACE_WARN:
                    logger.warn(format, argArray);
                    break;
                case TRACE_ERROR:
                    logger.error(format, argArray);
                    break;
            }
        }
    }

    /**
     * @param logger
     * @return
     */
    private static boolean isTraceable(Logger logger) {
        return logger != null ? getTraceLevel(TracerUtil.logger) >= getTraceLevel(logger) : false;
    }

    /**
     * 清除线程数据
     */
    public static void cleanTracer() {
        localTime.remove();
        localLogger.remove();
        localId.remove();
        localPointTime.remove();
    }

    public static boolean isTraceable() {
        return localLogger.get() != null;
    }

    /**
     * 子跟踪
     * @param name
     * @return
     */
    public static long subTrace(String name) {
        long ret = 0;
        Logger logger = localLogger.get();
        if (logger != null) {
            ret = getTraceId();
            Map<Long, Long> map = localPointTime.get();
            if (map == null) {
                map = new HashMap<Long, Long>();
                localPointTime.set(map);
            }
            map.put(ret, System.currentTimeMillis());
            traceOut(logger, ">>#:T-{}:{} start at {}ms\t\tsub-trace ST-{}.", localId.get(),
                name == null ? "" : name, System.currentTimeMillis() - localTime.get(), ret);
        }
        return ret;
    }

    /**
     * 跟踪消耗
     * @param name
     */
    public static void traceCost(String name) {
        traceCost(name, null);
    }

    /**
     * 跟踪消耗，子消耗（如果有）
     * @param name
     * @param subTraceId 使用{@link #subTrace(String)}获得的子跟踪号
     */
    public static void traceCost(String name, Long subTraceId) {
        Logger logger = localLogger.get();
        if (logger != null) {
            Long subStart = getSubStart(subTraceId);
            long now = System.currentTimeMillis();
            if (subStart != null) {
                traceOut(logger, ">>:T-{}:{} cost {}ms,\t\tST-{}: cost {}ms", localId.get(),
                    name == null ? "" : name, now - localTime.get(), subTraceId, now - subStart);
            } else {
                traceOut(logger, ">>:T-{}:{} cost {}ms", localId.get(), name == null ? "" : name,
                    now - localTime.get());
            }
        }
    }

    /**
     * 获得子跟踪开始时间
     * @param subTraceId
     * @return
     */
    private static Long getSubStart(Long subTraceId) {
        Long ret = null;
        if (subTraceId != null) {
            Map<Long, Long> map = localPointTime.get();
            if (map != null) {
                ret = map.get(subTraceId);
            }
        }
        return ret;
    }

    /**
     * 结束跟踪过程
     * @param name
     */
    public static void finishTrace(String name) {
        Logger logger = localLogger.get();
        if (logger != null) {
            traceOut(logger, "@:T-{}:{} total cost {}ms", localId.get(), name == null ? "" : name,
                System.currentTimeMillis() - localTime.get());
            cleanTracer();
        }
    }
}