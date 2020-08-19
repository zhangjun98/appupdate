package com.ztkj.platform.update.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/10 11:07
 * @Description: 页面转发 控制器
 */
@Controller
public class viewPageController {

        @RequestMapping(value="/index" ,method = RequestMethod.GET)
        public String index(HttpSession httpSession) {
            if(httpSession.getAttribute("name")!=null){
                return "adminIndex";
            }
            return "index";
        }

        @RequestMapping(value="/adminIndex" ,method = RequestMethod.GET)
        public String adminIndex(HttpSession httpSession) {
            if(httpSession.getAttribute("name")==null){
                return "index";
            }
        return "adminIndex";
    }

        @RequestMapping(value="/welcome" ,method = RequestMethod.GET)
        public String welcome(HttpSession httpSession) {
            if(httpSession.getAttribute("name")==null){
                return "index";
            }
        return "welcome";
    }

         @RequestMapping(value="/upload" ,method = RequestMethod.GET)
         public String upload(HttpSession httpSession) {
             if(httpSession.getAttribute("name")==null){
                 return "index";
             }
            return "upload";
         }

         @RequestMapping(value="/viewHistroy" ,method = RequestMethod.GET)
         public String viewHistroy(HttpSession httpSession) {
             if(httpSession.getAttribute("name")==null){
                 return "index";
             }
            return "viewHistroy";
         }

         @RequestMapping(value="/viewNew" ,method = RequestMethod.GET)
         public String viewNew(HttpSession httpSession) {
             if(httpSession.getAttribute("name")==null){
                 return "index";
             }
        return "viewNew";
    }
          @RequestMapping(value="/loginOut" ,method = RequestMethod.GET)
         public String loginOut(HttpSession httpSession) {
              if(httpSession.getAttribute("name")!=null){
                  httpSession.setAttribute("name",null);
              }
             return "index";
    }


}
