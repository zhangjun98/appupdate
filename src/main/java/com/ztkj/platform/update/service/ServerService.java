package com.ztkj.platform.update.service;


import com.ztkj.platform.update.Utils.CatchUtils;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.config.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 12:40
 * @Description:
 */
@Service
public class ServerService {
    @Autowired
    CatchUtils catchUtils;
    @Autowired
    Properties fileProp;

  public boolean addNewProductInfo(ProductEntity productEntity){
        return  catchUtils.addNewProduct(productEntity);
    }
    public boolean updateProductInfo(ProductEntity productEntity){
        return  catchUtils.updateNewproduct(productEntity);
    }
    public ProductEntity getProduct(String productid){
        if(fileProp.getCache()!=null&&!fileProp.getCache()){
            return catchUtils.getEntityFormDataBase(productid.trim());
        }
        else{
            return catchUtils.getEntity(productid);
        }

    }
}
