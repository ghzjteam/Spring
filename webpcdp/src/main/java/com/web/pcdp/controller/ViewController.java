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

<<<<<<< HEAD
    @RequestMapping("/group")
    public String group(){
        return "groups";
    }

=======
>>>>>>> c03938eddc2acb005e8a348f4ae7b480aae981f6
    @RequestMapping("/meeting")
    public String meeting(){
        return "meetings";
    }

    @RequestMapping("/gproject")
    public String gproject(){
        return "gprojects";
    }
}
