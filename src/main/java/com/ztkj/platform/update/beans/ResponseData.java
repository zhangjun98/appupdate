package com.ztkj.platform.update.beans;

import lombok.Data;

import java.util.Date;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/10 20:55
 * @Description:
 */
@Data
public class ResponseData {
    String productPackge;
    String VersionName;
    Integer versionCode;
    String  productFileName;
    Long FileSize;
    Integer updateMethod;
    String description;
    Date updateDate;

    public ResponseData(ProductEntity productEntity){
        productPackge=productEntity.getProductID();
        VersionName=productEntity.getProductVersionID();
        versionCode=productEntity.getVersionCode();
        productFileName= productEntity.getProductFileName();
        FileSize=productEntity.getProductFileSize();
        updateMethod=productEntity.getUpdateMethod();
        description=productEntity.getUpdateDirections();
        try {
            updateDate = new Date(productEntity.getTimeStamp());
        }catch (Exception e){
            updateDate=null;
        }

    }


}
