package com.ztkj.platform.update.Utils;

import com.ztkj.platform.update.config.Properties;
import com.ztkj.platform.update.loggmanger.LoggManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/10 09:02
 * @Description: 获取基本路径
 */
@Component
public class PathUtils {
    public static String parator = File.separator;
    @Autowired
    Properties fileProp;
    static String win = null;
    static String lin = null;

    @PostConstruct
    public void init() {
        win = fileProp.getWinbasepath();
        lin = fileProp.getLinuxbasepath();
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0 ? true : false;
    }

    public static String getBaseLocal() {
        String WinBashLocal = "F:" + parator + "基础文件上传下载";
        String LinBashLocal = "/home" + parator + "baseFile";
        if (isWindows()) {
            if (win != null) {
                WinBashLocal = win;
            }
            LoggManger.info("基本路径为"+WinBashLocal);
            return WinBashLocal;
        } else {
            if (lin != null) {
                LinBashLocal = lin;
            }
            LoggManger.info("基本路径为"+LinBashLocal);
            return LinBashLocal;
        }
    }


}
