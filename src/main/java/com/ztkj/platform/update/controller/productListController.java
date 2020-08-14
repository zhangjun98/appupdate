package com.ztkj.platform.update.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztkj.platform.update.common.Layui;
import com.ztkj.platform.update.service.ClientService;
import com.ztkj.platform.update.service.productService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/13 16:08
 * @Description:
 */
@Controller
@RequestMapping("/List")
public class productListController {
    @Autowired
    productService productService;
    @RequestMapping(value = "/getproductinfo")
    @ResponseBody
    public Object getproductinfo(@RequestParam("limit") String limit,
                                 @RequestParam("page") String page){
       return getproductinfoWithpage(limit,page,null);
    }

    @RequestMapping(value = "/getproductFormName")
    @ResponseBody
    public Object getproductinfoWithpage(@RequestParam("limit") String limit,
                                 @RequestParam("page") String page,
                                 @RequestParam("productPackge")String productPackge){
        IPage productListPage = productService.getProductListPage(Integer.parseInt(page),Integer.parseInt(limit) , productPackge);
        return Layui.data(Math.toIntExact(productListPage.getTotal()),productListPage.getRecords());
    }
    @RequestMapping(value = "/getallnewProduct")
    @ResponseBody
    public Object getallnewProduct(@RequestParam("limit") String limit,
                                   @RequestParam("page") String page){
        IPage productListPage = productService.getAllnew(Integer.parseInt(page), Integer.parseInt(limit));
        return Layui.data(Math.toIntExact(productListPage.getTotal()),productListPage.getRecords());
    }
}
