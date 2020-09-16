package com.ztkj.platform.update.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ztkj.platform.update.Utils.CatchUtils;
import com.ztkj.platform.update.loggmanger.LoggManger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author ：zhangjun
 * @date ：Created in 2020/9/15 19:34
 * @description： TODO
 */
@Slf4j
@Component
public class StartedUpRunner   implements ApplicationRunner {



    @Value("${server.servlet.context-path:}")
    private String contextPath;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String  port = String.valueOf(ServerConfig.getPort());
        if (CatchUtils.install()){
            LoggManger.info("日志管理器注册成功");
            LoggManger.info("缓存管理器注册成功");
        }
        LoggManger.info(" __    ___   _      ___   _     ____ _____  ____ ");
        LoggManger.info("/ /`  / / \\ | |\\/| | |_) | |   | |_   | |  | |_  ");
        LoggManger.info("\\_\\_, \\_\\_/ |_|  | |_|   |_|__ |_|__  |_|  |_|__ ");
        LoggManger.info("APP更新系统启动成功 ");
        InetAddress address = InetAddress.getLocalHost();
        String url = String.format("http://%s:%s", address.getHostAddress(), port);
        String loginURL = "/platform-update/index";
        if (StringUtils.isNotBlank(contextPath)) {
            url += contextPath;
        }
        if (StringUtils.isNotBlank(loginURL)) {
            url += loginURL;
        }
            //使用默认浏览器打开系统登录页
        LoggManger.info(url);
   //  Runtime.getRuntime().exec("cmd  /c  start " + url);

    }


}
