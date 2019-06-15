package com.web.pcdp.controller;

import com.web.pcdp.config.Preferences;
import com.web.pcdp.domain.User;
import com.web.pcdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    @Qualifier("user")
    private UserService userService;

    //login界面跳转
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index(@RequestParam("user_id") int user_id,Model model){
        User user = userService.findUser(user_id);
        model.addAttribute("curruser", user);
        if(user!=null){
            return "index";
        }
        else
            return "login";
    }

    //个人信息
    @GetMapping("/settings")
    public String settings(Model model) {
        User oUser = userService.findUser(1);
        if(oUser!=null) {
            model.addAttribute("curruser", oUser);
        }
        return "settings";
    }

    //登陆
    @PostMapping("/Login")
    public String loginSubmit(String id, String password, Model model, HttpServletRequest request) {
        //System.out.println(id +"=" +password);
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
        //session 传入了用户id  使用session.session_user_id调用
        HttpSession session = request.getSession();
        session.setAttribute("session_user_id",id);

        model.addAttribute("id", id);
        model.addAttribute("success", success);
        if(success) {
            //String src = users.getPhoto();
            //users.setPhoto(Preferences.EXTERNAL_PATH+Preferences.HEAD_IMG_PATH+src);
            //System.out.println(users.toString());
            model.addAttribute("curruser", user);
            return "index";
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

            //System.out.println(user.toString());
            User oUser = userService.findUser(idnum);
            if(oUser == null) {
                userService.insertUser(user);
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

    //修改信息
    @PostMapping("/settingsSubmit")
    public String settingsSubmit(String number,String name,String email,String phone,String gender,Model model) {
        String message = "";
        User user = new User();
        boolean flag = false;
        try {
            int idnum = Integer.parseInt(number);
            user.setUserId(idnum);
            User oUser = userService.findUser(idnum);
            user=oUser;
            user.setUserName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setGender(gender);
            System.out.println(user.toString());
            if(oUser!= null) {
                userService.alterUser(user);
            }
            else {
                message = "账号不存在";
            }
        } catch (Exception e) {
            message = "转换或者sql错误";
            e.printStackTrace();
        }
        //System.out.println(message);
        model.addAttribute("curruser",user);
        return "index";
    }

    //修改密码
    @PostMapping("/changePasswordSubmit")
    public String changePasswordSubmit(String number,String password,Model model) {
        String message = "";
        //System.out.println("111----"+number+"\t"+password);
        try {
            int idnum = Integer.parseInt(number);
            User user = userService.findUser(idnum);

            user.setPassword(password);
            System.out.println(user.toString());

            if(user != null ) {
                userService.alterUser(user);

            }
            else {
                message = "账号不存在";
            }
        } catch (Exception e) {
            message = "转换或者sql错误";
            e.printStackTrace();
        }
        //System.out.println(message);
        model.addAttribute("id", number);
        return "settings";
    }
}
