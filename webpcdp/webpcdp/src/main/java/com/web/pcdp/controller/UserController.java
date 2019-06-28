package com.web.pcdp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.pcdp.config.Preferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.pcdp.domain.User;
import com.web.pcdp.service.UserService;

/**
* Title: UserController.java 

* Description:   UserController

* @author Guo_Jinhang  

* @date 2019年6月16日  

* @version 1.0  
 */
@Controller
public class UserController {

    @Autowired
    @Qualifier("user")
    private UserService userService;

    //login界面跳转
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
    	request.getSession().removeAttribute("session_user_id");
    	request.getSession().removeAttribute("login_user");
        request.getSession().removeAttribute("curruser");
    	return "login";
    }
    @GetMapping("/")
    public String home(HttpServletRequest request){  
    	return login(request);
    }
    @GetMapping("/index")
    public String index(Model model,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("login_user");
        if(user!=null){
        	model.addAttribute("curruser", user);
            return "index";
        }
        else
            return "redirect:login";
    }
    @GetMapping("/register")
    public String register(Model model) {
    	model.addAttribute("id", userService.getNextUserID()+1);
        return "register";
    }
    
    
    //个人信息
    @GetMapping("/settings")
    public String settings(Model model,HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	int id = (int)session.getAttribute("session_user_id");
        User oUser = userService.findUser(id);
        if(oUser!=null) {
            model.addAttribute("curruser", oUser);
        }
        return "settings";
    }

    //登陆
    @PostMapping("/Login")
    public String loginSubmit(String id, String password, Model model, HttpServletRequest request) {
        System.out.println("ID：" +id +"	PSW：" +password);
        String message = "";
        int idnum = -1;
       
        boolean success = false;
        User user = new User();
        try {
            idnum = Integer.parseInt(id);
            User oUser = userService.findUser(idnum);
            if(oUser != null) {
                if(password.equals(oUser.getPassword())) {
                    user = oUser;
                    System.out.println("登录成功");
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
//String src = users.getPhoto();
//users.setPhoto(Preferences.EXTERNAL_PATH+Preferences.HEAD_IMG_PATH+src);
//System.out.println(users.toString());
//session 传入了用户id  使用session.session_user_id调用
        	HttpSession session = request.getSession();
            session.setAttribute("session_user_id",idnum);
            session.setAttribute("login_user",user);
            session.setAttribute("curruser",user);
            return "redirect:index";
        }
        else {
            return "login";
        }
    }

    //注册验证
    @PostMapping("/registerSubmit")
    public String registerSubmit(String number,String name,String password,String email,String phone,String gender,Model model) {
        String message = "";
        User user = new User();
        boolean success = false;
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
            User oUser = userService.findUser(idnum);
            if(oUser == null) {
                userService.insertUser(user);
            	System.out.println("注册");
            	success = true;
            }
            else {
                message = "账号已存在";
            }
        } catch (Exception e) {
            message = "转换或者sql错误";
            e.printStackTrace();
        }
        System.out.println(message);
        if(success) {
            model.addAttribute("id", number);
            return "login";
        }
        else {
            model.addAttribute("number", number);
            model.addAttribute("success", success);
            return "register";
        }
    }

    //修改信息
    @PostMapping("/settingsSubmit")
    public String settingsSubmit(String number,String name,String email,String phone,String gender,Model model,HttpServletRequest request) {
        String message = "";
        User user = new User();
        try {
            int idnum = Integer.parseInt(number);
            user.setUserId(idnum);
            user = userService.findUser(idnum);
            user.setUserName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setGender(gender);
            System.out.println("dsa+"+user.toString());
            if(user!= null) {
                userService.alterUser(user);
            }
        } catch (Exception e) {
            message = "转换或者sql错误";
            e.printStackTrace();
        }
        //System.out.println(message);
        request.getSession().setAttribute("curruser",user);
        request.getSession().setAttribute("login_user",user);
        model.addAttribute("curruser",user);
        return "redirect:index";
    }

    //修改密码
    @PostMapping("/changePasswordSubmit")
    public String changePasswordSubmit(String number,String password,Model model) {
        String message = "";
        //System.out.println("111----"+number+"\t"+password);
        try {
        	System.out.println();
            int idnum = Integer.parseInt(number);
            User user = userService.findUser(idnum);

            user.setPassword(password);
            System.out.println(user.toString());

            if(user != null ) {
                userService.updataPassword(user);
            }
        } catch (Exception e) {
            message = "转换或者sql错误";
            e.printStackTrace();
        }
        //System.out.println(message);
        model.addAttribute("id", number);
        return "login";
    }
    
    //获得自动构建的用户ID值
    @RequestMapping("/getNextUserID")
    @ResponseBody
    public  int getNextUserID() {
		return userService.getNextUserID()+1;
    }
}
