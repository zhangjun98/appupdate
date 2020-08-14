package com.ztkj.platform.update.Utils;

import org.springframework.util.unit.DataSize;

import java.io.File;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/10 09:02
 * @Description: 获取基本路径
 */
public class PathUtils {
    public static String parator=File.separator;
    private static String WinBashLocal="D:"+ parator +"基础文件上传下载";
    private static String LinBashLocal="/home"+ parator+"baseFile";


    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")>=0?true:false;
    }

    public static String getBaseLocal(){
        if(isWindows()){
            return WinBashLocal;
        }else{
            return LinBashLocal;
        }
    }

  /*  public static void main(String[] args) {
        System.out.println(DataSize.parse("120MB"));
        ;
    }*/
}
