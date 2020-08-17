package com.ztkj.platform.update.service;

import com.ztkj.platform.update.Utils.CatchUtils;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.config.Properties;
import com.ztkj.platform.update.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 09:59
 * @Description:  客户端服务类
 */
@Service
public class ClientService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CatchUtils catchUtils;
    @Autowired
    Properties fileProp;
    public Integer getNewVersion( String productid){
        if(fileProp.getCache()!=null&&!fileProp.getCache()){
            return catchUtils.getNewVersionFormDatabase(productid.trim());
        }
       else{
            return catchUtils.getNewVersion(productid.trim());
        }
    }

    public ProductEntity getProduct(String productid){
        if(fileProp.getCache()!=null&&!fileProp.getCache()){
            return catchUtils.getEntityFormDataBase(productid.trim());
        }else{
            return catchUtils.getEntity(productid);
        }
    }

    /**
     * 返回历史记录中离当前时间最近的记录
     * @param productid
     * @param versioncode
     * @return
     */
    public ProductEntity getHistroyProductOne(String productid,Integer versioncode){
        List<ProductEntity> histroyEntityList = catchUtils.getHistroyEntityList(productid, versioncode);
        Long TimeStamp = 0L;
        ProductEntity productEntitytemp=null;
        for(ProductEntity productEntity:histroyEntityList){
            if(productEntity.getTimeStamp()>TimeStamp){
                TimeStamp=productEntity.getTimeStamp();
                productEntitytemp=productEntity;
            }
        }
        return productEntitytemp;
    }

}
