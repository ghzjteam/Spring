package com.web.pcdp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.*;

@SpringBootApplication
@ServletComponentScan
public class PcdpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcdpApplication.class, args);
    }

}
