package com.web.pcdp.controller;

import com.web.pcdp.repository.TestRepository;
import com.web.pcdp.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {
    @Autowired
    TestRepository testRepository;

    @RequestMapping(value = "/findAllTest",method = RequestMethod.GET)
    @ResponseBody
    public List<Test> findAllTest(){
        List<Test> testList=testRepository.findAll();
        for(int i=0;i<testList.size();i++) {
            System.out.println(testList.get(i));
        }
        return testList;
    }


}
