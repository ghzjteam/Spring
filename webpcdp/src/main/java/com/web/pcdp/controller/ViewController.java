package com.web.pcdp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("/project")
    public String project(){
        return "projects";
    }

    @RequestMapping("/group")
    public String group(){
        return "groups";
    }

    @RequestMapping("/meeting")
    public String meeting(){
        return "meetings";
    }

    @RequestMapping("/gproject")
    public String gproject(){
        return "gprojects";
    }
}
