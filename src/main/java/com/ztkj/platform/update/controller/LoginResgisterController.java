package com.ztkj.platform.update.controller;


import com.ztkj.platform.update.config.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


@Controller
public class LoginResgisterController {
	@Autowired
	Properties fileProp;
    @ResponseBody
	@RequestMapping(value = "/dealLogin",method = RequestMethod.POST)
	public String getInfo(@RequestParam(value = "num") String num, @RequestParam(value = "psw") String psw, HttpSession httpSession) {
    			String username="admin";
    			String password="admin";
    			if(fileProp.getUsername()!=null){
					username=fileProp.getUsername();
				}
				if(fileProp.getPassword()!=null){
					password=fileProp.getPassword();
				}
				if(num.equals(username)&&psw.equals(password)){
				httpSession.setAttribute("account", num);
				httpSession.setAttribute("name", num);
				httpSession.setAttribute("photo", "admin.png");
				httpSession.setAttribute("role", "admin");
				return "success";
				}
				return "fail";
	}
		@RequestMapping("/loginView")
	public String loginView(HttpSession httpSession) {
		return "redirect:/index";
	}

		@RequestMapping("/loginOut")
		public String loginOut(HttpSession httpSession) {
		httpSession.removeAttribute("name");
		httpSession.removeAttribute("account");
		httpSession.removeAttribute("photo");
		return "redirect:/loginView";
		}
}
