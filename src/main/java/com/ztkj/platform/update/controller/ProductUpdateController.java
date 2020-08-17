package com.ztkj.platform.update.controller;

import com.ztkj.platform.update.Utils.CatchUtils;
import com.ztkj.platform.update.Utils.FileUtils;
import com.ztkj.platform.update.Utils.PathUtils;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.common.CommonResponse;
import com.ztkj.platform.update.common.ResponseCode;
import com.ztkj.platform.update.service.ServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 09:26
 * @Description: 更新产品包的 控制器
 */
@Controller
@RequestMapping("/server")
@Api("服务器更新控制器")
public class ProductUpdateController {
    @Autowired
    ServerService serverService;


    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation("上传接口")
    public CommonResponse upload(@RequestParam String productPackge,
                                 @RequestParam String versionName,
                                 @RequestParam Integer versionCode,
                                 @RequestParam Integer updateMethod,
                                 @RequestParam String updateDirections,
                                 @RequestParam Integer runmethod,
                                 @RequestParam(value = "file",required = false)  MultipartFile file) {
    if(productPackge.trim().equals("")||versionName.trim().equals("")||updateDirections.trim().equals("")){
        return new CommonResponse(ResponseCode.Wrong_Input_Information,"输入信息有误") ;
    }
        ProductEntity product = serverService.getProduct(productPackge);
    if(product!=null){
        Integer versionCode1 = product.getVersionCode();
        if(versionCode<versionCode1){
            return new CommonResponse(ResponseCode.File_Failed_To_Upload,"服务器有更高版本号") ;
        }
    }
        if (file==null||file.isEmpty()){
            if(runmethod==1){ return new CommonResponse(ResponseCode.Failed_To_Upload_Because_File_Is_Empty,"文件有误") ;}
           else{
                ProductEntity emptyEntity = CatchUtils.getEmptyEntity();
                emptyEntity.setIsNew(1);
                emptyEntity.setUpdateDirections(updateDirections);
                emptyEntity.setProductID(productPackge);
                emptyEntity.setProductVersionID(versionName);
                emptyEntity.setVersionCode(versionCode);
                emptyEntity.setUpdateMethod(updateMethod);
                emptyEntity.setTimeStamp(System.currentTimeMillis());
                 serverService.updateProductInfo(emptyEntity);
                return new CommonResponse(ResponseCode.Failed_To_Upload_Because_File_Is_Empty,"更新成功");
            }
        }else{
        // 校验后缀名
        String fileName = file.getOriginalFilename();
        if(fileName.indexOf("apk")==-1){
            return new CommonResponse(ResponseCode.Wrong_File_Name,"文件类型只能为apk类型") ;
        }
        try{
            FileUtils.makeDir(productPackge, String.valueOf(versionCode));
        }catch (Exception e){
            return new CommonResponse(ResponseCode.File_Failed_To_Upload,"文件服务器加载异常");
        }
        File pathFile= FileUtils.getFilePath(productPackge, String.valueOf(versionCode));
        if(pathFile==null){
            return new CommonResponse(ResponseCode.File_Failed_To_Upload,"上传失败");
        }
        String filePath =pathFile.getAbsolutePath();
        File dest = new File(filePath + PathUtils.parator +fileName);
        if(dest.exists()){
            dest= FileUtils.getAvailableNameFile(filePath + PathUtils.parator ,fileName);
        }
        try {
            file.transferTo(dest);
            long size = file.getSize();
            ProductEntity emptyEntity = CatchUtils.getEmptyEntity();
            emptyEntity.setIsNew(1);
            emptyEntity.setProductFileSize(size);
            emptyEntity.setUpdateDirections(updateDirections);
            emptyEntity.setProductID(productPackge);
            emptyEntity.setProductVersionID(versionName);
            emptyEntity.setVersionCode(versionCode);
            emptyEntity.setUpdateMethod(updateMethod);
            emptyEntity.setProductFileName(dest.getName());
            boolean b = serverService.addNewProductInfo(emptyEntity);
            if(b){
                return new CommonResponse(ResponseCode.File_Upload_Success,"上传成功") ;
            }else{
                return new CommonResponse(ResponseCode.Repeat_upload,"上传失败") ;
            }

        } catch (IOException e) {
        }
        }
        return new CommonResponse(ResponseCode.File_Failed_To_Upload,"上传失败") ;
    }


}
