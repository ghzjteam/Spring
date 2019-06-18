package com.web.pcdp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.pcdp.entity.User;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(HttpSession session) {
User users =(User) session.getAttribute("currLoginUser");
		if(users!=null) {
			return "index";
		}
		else {
			return "redirect:login";
		}
	}
}
