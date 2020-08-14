package com.ztkj.platform.update.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztkj.platform.update.Utils.CatchUtils;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/13 16:17
 * @Description:
 */
@Service
public class productService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CatchUtils catchUtils;
    public IPage getProductListPage(int limit,int page,String productPackgeName){
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        if(productPackgeName!=null){
            wrapper.like("productID",productPackgeName);
        }
        wrapper.orderByDesc("updateDate");
        Page<ProductEntity> page1 = new Page<>(limit, page);
        IPage<ProductEntity> userIPage = productMapper.selectPage(page1, wrapper);
       return userIPage;
    }
    public IPage getAllnew(int limit,int page){
        QueryWrapper<ProductEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("isNew",1);
        wrapper.orderByDesc("updateDate");
        Page<ProductEntity> page1 = new Page<>(limit, page);
        IPage<ProductEntity> userIPage = productMapper.selectPage(page1, wrapper);
        return userIPage;
    }

}
