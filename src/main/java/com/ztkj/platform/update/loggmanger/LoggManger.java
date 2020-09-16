package com.ztkj.platform.update.loggmanger;


import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 13:05
 * @Description: 手动日志统一入口
 */
@Slf4j
public class LoggManger  {
    public  static void warn(Object message, Throwable t) {
        log.warn(message.toString(),t);
    }
    public static void error(Object message, Throwable t) {
        log.error(message.toString(),t);
    }
    public static void info(Object message, Throwable t) {
        log.info(message.toString(),t);
    }
    public static void info(Object message) {
        log.info(message.toString());
    }
    public static void debug(Object message, Throwable t) {
        log.debug(message.toString(),t);
    }


}
