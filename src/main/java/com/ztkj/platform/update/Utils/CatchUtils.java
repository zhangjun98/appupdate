package com.ztkj.platform.update.Utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.config.Properties;
import com.ztkj.platform.update.loggmanger.LoggManger;
import com.ztkj.platform.update.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 10:43
 * @Description: 数据缓存
 */
@Component
public class CatchUtils {
    @Autowired
    Properties fileProp;

    private static Long beginTime =System.currentTimeMillis();

    private static Long refreshtime=1000*60*60L;

    @PostConstruct
    public void init(){
        refreshtime=fileProp.getRefreshtime();
    }
    /**
     * 设置缓存失效时间
     */
    public static boolean install(){
        Thread    thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    while (true){
                        //求得是毫秒时间差
                        long startTime = System.currentTimeMillis() - beginTime;
                        //一小时清空一次缓存
                        if(startTime>refreshtime){
                            isInit=false;
                            beginTime =System.currentTimeMillis();
                            if(fileVersionMap!=null){
                                fileVersionMap.clear();
                                LoggManger.info("缓存清空");
                            }
                        }
                    }
            }catch (Exception ex){
                    LoggManger.error("日志管理器运行出错," + ex.getMessage(),ex);
            }
        }
    });
        thread.setDaemon(true);
        thread.start();
        return true;
}
    @Autowired
    ProductMapper productmapper;

    static boolean isInit=false;
    /**
     * 用于 存储最新版本
     */
    private static ConcurrentHashMap<String,ProductEntity> fileVersionMap;

    /**
     * 获取最新版本
     * @param productID
     * @return
     */
    public Integer getNewVersion(String productID){
        Integer version=null;
       if(!isInit){
           initMap();
       }

        ProductEntity productEntity = fileVersionMap.get(productID.trim());
        if(productEntity==null){
               version = getNewVersionFormDatabase(productID.trim());
         }else{
           version = productEntity.getVersionCode();
        }

       return version;
    }
    public static ProductEntity getEmptyEntity(){
        ProductEntity productEntity = new ProductEntity();
        return productEntity;
    }
    public ProductEntity getEntity(String productID){
        ProductEntity productEntity=null;
        if(!isInit){
            initMap();
        }
          productEntity = fileVersionMap.get(productID.trim());
          if(productEntity==null){
              productEntity=getEntityFormDataBase(productID.trim());
              addProductToMap(productEntity);
          }

        return productEntity;
    }
    /**
     * 将 实体添加到 缓存中
     * @param productentity
     */
    private void addProductToMap(ProductEntity productentity){
        if(productentity!=null){
            fileVersionMap.put(productentity.getProductID().trim(),productentity);
        }
    }
    public boolean addNewProduct (ProductEntity productentity){
        boolean isupdate= false;
        if(!isInit){
            initMap();
        }
        if(addNewProductFormDatabase(productentity)){
            addProductToMap(productentity);
            isupdate=true;
        }
        return isupdate;
    }
    /**
     * 添加某一个产品的版本与版本号
     * @param productentity
     * @return
     */
    public boolean updateNewproduct(ProductEntity productentity){
        boolean isupdate= false;
        if(!isInit){
            initMap();
        }
        ProductEntity productEntity1 = fileVersionMap.get(productentity.getProductID());
        if(productEntity1!=null){
            productEntity1.setTimeStamp(productentity.getTimeStamp());
            productEntity1.setUpdateMethod(productentity.getUpdateMethod());
            productEntity1.setUpdateDirections(productentity.getUpdateDirections());
            productmapper.updateById(productEntity1);
            addProductToMap(productEntity1);
            isupdate=true;
        }
        return isupdate;
    }
    /**
     * 添加到数据库
     * @param productentity
     * @return
     */
    private synchronized boolean addNewProductFormDatabase(ProductEntity productentity){
        productentity.setTimeStamp(System.currentTimeMillis());
        boolean isadd=false;
        ProductEntity productEntity1 = fileVersionMap.get(productentity.getProductID());
        if(productEntity1!=null){
            //判断是否为重复上传
            if(productEntity1.getVersionCode().intValue()==productentity.getVersionCode().intValue()){
                //改成更新 将旧的记录设置 为旧版本 2 插入数据库
                productentity.setPkid(productEntity1.getPkid());
                //productentity.setTimeStamp(System.currentTimeMillis());
                int i = productmapper.updateById(productentity);
                productEntity1.setPkid(null);
                productEntity1.setIsNew(2);
                productmapper.insert(productEntity1);
                return i==1?true:false;
            }
            //2 为历史版本
            productEntity1.setIsNew(2);
            int i = productmapper.updateById(productEntity1);
            if(i==1&& productmapper.insert(productentity)==1){
                isadd=true;
            }
        }else{
            int insert = productmapper.insert(productentity);
            if(insert==1){
                isadd=true;
            }
        }
        return isadd;
    }
    public synchronized ProductEntity getEntityFormDataBase(String id){
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("isNew",1);
        wrapper.eq("productID",id);
        ProductEntity productEntity = productmapper.selectOne(wrapper);
        return productEntity;
    }
    /**
     * 从数据库获取版本号
     * @param id
     * @return
     */
    public synchronized Integer getNewVersionFormDatabase(String id){
        Integer version=null;
        ProductEntity productEntity = getEntityFormDataBase(id);
        if(productEntity!=null&&productEntity.getProductVersionID()!=null&&productEntity.getProductID()!=null){
            version= productEntity.getVersionCode();
            fileVersionMap.put(productEntity.getProductID().trim(),productEntity);
        }
        return version;
    }
    private synchronized void initMap(){
        if(!isInit){
        fileVersionMap=new ConcurrentHashMap<String,ProductEntity>();
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("isNew",1);
        //如果数据库中有两个同时为1的新版本
        List<ProductEntity> productEntities = productmapper.selectList(wrapper);
        for(ProductEntity productEntity :productEntities){
            String productID = productEntity.getProductID();
            String productVersionID = productEntity.getProductVersionID();
            if(!StringUtils.isEmpty(productID)&&!StringUtils.isEmpty(productVersionID)){
                ProductEntity productEntity1 = fileVersionMap.get(productID.trim());
                if(productEntity1!=null){
                    //缓存中已存在的产品
                    Integer versionCode = productEntity1.getVersionCode();
                    if(versionCode.intValue()<productEntity.getVersionCode().intValue()){
                        fileVersionMap.put(productID.trim(),productEntity);
                    }
                }else{
                    fileVersionMap.put(productID.trim(),productEntity);
                }

            }
        }
        isInit=true;
    }
    }

    public List<ProductEntity> getHistroyEntityList(String productid,Integer versioncode){
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("productID",productid);
        wrapper.eq("VersionCode",versioncode);
        List<ProductEntity> productEntities  =productmapper.selectList(wrapper);
        return productEntities;
    }
}
