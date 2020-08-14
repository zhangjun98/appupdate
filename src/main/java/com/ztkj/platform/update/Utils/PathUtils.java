package com.ztkj.platform.update.Utils;

import java.io.File;
import java.util.Properties;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/10 09:02
 * @Description: 获取基本路径
 */
public class PathUtils {
    public static String parator=File.separator;

   static String win = null;
    static String lin = null;
    public static void init (String win,String lin){
        PathUtils.win=win;
        PathUtils.lin=lin;
    }


    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")>=0?true:false;
    }

    public   String getBaseLocal(){
       Properties properties = new Properties();
       String WinBashLocal="F:"+ parator +"基础文件上传下载";
        String LinBashLocal="/home"+ parator+"baseFile";
        if(isWindows()){
            if(win!=null){
                WinBashLocal=win;
                System.out.println("来自文件=== "+WinBashLocal);
            }
            return WinBashLocal;
        }else{
            if(lin!=null){
                LinBashLocal=lin;
            }
            return LinBashLocal;
        }
    }


}
