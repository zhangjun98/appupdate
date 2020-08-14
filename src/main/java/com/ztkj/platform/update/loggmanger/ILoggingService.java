package com.ztkj.platform.update.loggmanger;

/**
 * @Author: Administrator
 * @Date: 2020/8/8 13:07
 * @Description:
 */
public interface ILoggingService {
    /**
     * 警告日志
     * @param message 日志信息
     * @param t 异常捕获
     */
    void warn(Object message, Throwable t);

    /**
     * 错误日志
     * @param message 日志信息
     * @param t 异常捕获
     */
    void error(Object message, Throwable t);

    /**
     * 输出信息
     * @param message 日志信息
     * @param t 异常捕获
     */
    void info(Object message, Throwable t);

    /**
     * 调试日志
     * @param message 日志信息
     * @param t 异常捕获
     */
    void debug(Object message, Throwable t);

    /**
     * 操作日志
     * @param message 日志信息
     */
    void operation(Object message);

}
