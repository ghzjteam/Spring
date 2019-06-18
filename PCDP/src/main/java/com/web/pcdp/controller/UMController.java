package com.web.pcdp.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.pcdp.Preferences;
import com.web.pcdp.entity.User;
import com.web.pcdp.repository.TeamRepository;
import com.web.pcdp.repository.UserRepository;
/**
* Title: UMController.java 

* Description:   

* @author Guo_Jinhang  

* @date 2019年6月13日  

* @version 1.0  
 */
@Controller
public class UMController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TeamRepository teamsRepository;
	
	@GetMapping("/login")
	public String login(HttpSession session) {
		session.removeAttribute("currLoginUser");
		
		return "login";
	}
	@GetMapping("/settings")
	public String settings(Model model,HttpSession session) {
		User users =(User) session.getAttribute("currLoginUser");
		Optional<User> oUser = userRepository.findById(users.getUserId());
		if(oUser.isPresent()) {
			model.addAttribute("curruser", oUser.get());
		}

		return "settings";
	}
	@PostMapping("/loginSubmit")
	public String loginSubmit(String id,String password,Model model,HttpSession session) {
		System.out.println(id +"=" +password);
		String message = "";
		int idnum = -1;
		boolean success = false;
		User users = new User();
		try {
			idnum = Integer.parseInt(id);
			Optional<User> oUser = userRepository.findById(idnum);
			if(oUser.isPresent()) {
				if(password.equals(oUser.get().getPassword())) {
					users = oUser.get();
					session.setAttribute("currLoginUser", users);
					success = true;
				}
				else {
					System.out.println("密码错误");
				}
			}
			else {
				System.out.println("没有找到账号");
			}
		} catch (Exception e) {
			 message = "转换错误";
		}
		model.addAttribute("id", id);
		model.addAttribute("success", success);
		if(success) {
			String src = users.getPhoto();
			users.setPhoto(Preferences.EXTERNAL_PATH+Preferences.HEAD_IMG_PATH+src);
			System.out.println(users.toString());
			model.addAttribute("curruser", users);
			return "index";
		}
		else {
			return "login";
		}
	}
	@PostMapping("/registerSubmit")
	public String registerSubmit(String number,String name,String password,String email,String phone,String gender,Model model) {
		String message = "";
		User user = new User();
		boolean flag = false;
		try {
			int idnum = Integer.parseInt(number);
			user.setUserName(name);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setGender(gender);
			user.setUserId(idnum);
			user.setRegDate(Preferences.getDateTime());
			user.setPhoto(Preferences.DEFAULT_PHOTO);
			
			System.out.println(user.toString());
			Optional<User> oUser = userRepository.findById(idnum);
			if(!oUser.isPresent()) {
				userRepository.save(user);
				flag = true;
			}
			else {
				message = "账号已存在";
			}
		} catch (Exception e) {
			 message = "转换或者sql错误";
			 e.printStackTrace();
		}
		System.out.println(message);
		if(flag) {
			model.addAttribute("id", number);
			return "login";
		}
		else {
			model.addAttribute("number", number);
			return "login#signup";
		}
	}
	@PostMapping("/settingsSubmit")
	public String settingsSubmit(String number,String name,String email,String phone,String gender,Model model) {
		String message = "";
		User user = new User();
		boolean flag = false;
		try {
			int idnum = Integer.parseInt(number);
			user.setUserId(idnum);
			Optional<User> oUser = userRepository.findById(idnum);
			user=oUser.get();
			user.setUserName(name);
			user.setEmail(email);
			user.setPhone(phone);
			user.setGender(gender);
			System.out.println(user.toString());
			if(oUser.isPresent()) {
				userRepository.save(user);
				flag = true;
			}
			else {
				message = "账号不存在";
			}
		} catch (Exception e) {
			 message = "转换或者sql错误";
			 e.printStackTrace();
		}
		System.out.println(message);

		model.addAttribute("curruser",user);
		return "index";
	}
	
	@PostMapping("/changePasswordSubmit")
	public String changePasswordSubmit(String number,String password,Model model) {
		
		System.out.println(number+"+"+password);
		String message = "";
		User user = new User();
		boolean flag = false;
		try {
			int idnum = Integer.parseInt(number);
			Optional<User> oUser = userRepository.findById(idnum);
			user = oUser.get();
			user.setPassword(password);
			System.out.println(user.toString());

			if(oUser.isPresent()) {
				userRepository.save(user);
				flag = true;
			}
			else {
				message = "账号不存在";
			}
		} catch (Exception e) {
			 message = "转换或者sql错误";
			 e.printStackTrace();
		}
		System.out.println(message);
	
		if(user!=null) {
			model.addAttribute("curruser", user);
		}
		return "settings";
	}
}
