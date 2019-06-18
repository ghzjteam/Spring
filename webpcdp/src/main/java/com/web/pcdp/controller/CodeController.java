package com.web.pcdp.controller;

import com.web.pcdp.domain.User;
import com.web.pcdp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CodeController {
    @Autowired
    @Qualifier("user")
    private UserService userService;

    @GetMapping("/code")
    public String code(){
        return "code";
    }

    @RequestMapping("/compare")
    public String compare() {
        return "code";
    }
}
