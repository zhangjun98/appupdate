package com.ztkj.platform.update.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


@Controller
public class LoginResgisterController {

    @ResponseBody
	@RequestMapping(value = "/dealLogin",method = RequestMethod.POST)
	public String getInfo(@RequestParam(value = "num") String num, @RequestParam(value = "psw") String psw, HttpSession httpSession) {
				if(num.equals("admin")&&psw.equals("admin")){
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
