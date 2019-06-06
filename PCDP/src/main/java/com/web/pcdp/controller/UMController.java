package com.web.pcdp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.pcdp.Preferences;
import com.web.pcdp.entity.Teams;
import com.web.pcdp.entity.Users;
import com.web.pcdp.repository.TeamsRepository;
import com.web.pcdp.repository.UsersRepository;

@Controller
public class UMController {
	@Autowired
	UsersRepository userRepository;
	
	@Autowired
	TeamsRepository teamsRepository;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/settings")
	public String settings(Model model) {
		Optional<Users> oUser = userRepository.findById(1);
		if(oUser.isPresent()) {
			model.addAttribute("curruser", oUser.get());
		}

		return "settings";
	}
	@PostMapping("/loginSubmit")
	public String loginSubmit(String id,String password,Model model) {
		System.out.println(id +"=" +password);
		String message = "";
		int idnum = -1;
		boolean success = false;
		Users users = new Users();
		try {
			idnum = Integer.parseInt(id);
			Optional<Users> oUser = userRepository.findById(idnum);
			if(oUser.isPresent()) {
				if(password.equals(oUser.get().getPassword())) {
					users = oUser.get();
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
		Users user = new Users();
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
			Optional<Users> oUser = userRepository.findById(idnum);
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
		Users user = new Users();
		boolean flag = false;
		try {
			int idnum = Integer.parseInt(number);
			user.setUserId(idnum);
			Optional<Users> oUser = userRepository.findById(idnum);
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
		String message = "";
		Users user = new Users();
		boolean flag = false;
		try {
			int idnum = Integer.parseInt(number);
			Optional<Users> oUser = userRepository.findById(idnum);
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

		model.addAttribute("id", number);
		return "settings";
	}
	@GetMapping("/allteams")
    public String allteams(Model model) {
		List<Teams> ul = new ArrayList<>();

		teamsRepository.findAll();
		Iterable<Teams> ui = teamsRepository.findAll();
		if(ui != null) {
			for(Teams item :ui) {
				ul.add(item);
				System.out.println(item.getTeamId());
			}
		}
		model.addAttribute("list", ul);
    	return "query2";
    }
	@GetMapping("/allusers")
    public String all(Model model) {
    	
		Iterable<Users> all = userRepository.findAll();
		
		List<Users> userList = new ArrayList<Users>();
		if (all != null) {
			for (Users item : all) {
				String src = item.getPhoto();
				item.setPhoto(Preferences.EXTERNAL_PATH+Preferences.HEAD_IMG_PATH+src);
				userList.add(item);
			}
		}
		model.addAttribute("userList", userList);
    	return "query";
    }
}
