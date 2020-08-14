package com.ztkj.platform.update.common;

public interface ResponseCode {
    // 校验新版本的返回值
    /**
     * 本地是最新版本
     */
    public static String IsNewVersion="201";
    /**
     * 没有该版本对应产品
     */
    public static String NO_This_Product_Version="202";
    /**
     * 有新版本
     */
    public static String Has_New_Version="200";
    /**
     * 下载成功
     */
    public static String Download_Scuess="200";

    // 文件下载的相关返回值
    /**
     * 输入信息有误
     */
    public static String Wrong_Input_Information="301";


    //文件上传相关返回值
    /**
     * 待上传的文件为空
     */
    public static String Failed_To_Upload_Because_File_Is_Empty="401";

    /**
     * 错误的文件类型
     */
    public static String Wrong_File_Name="402";

    /**
     * 文件上传失败
     */
    public static String File_Failed_To_Upload="403";
    /**
     * 文件上传成功
    */
    public static String File_Upload_Success="200";
    /**
     * 重复上传
     */
    public static String Repeat_upload="405";


}
