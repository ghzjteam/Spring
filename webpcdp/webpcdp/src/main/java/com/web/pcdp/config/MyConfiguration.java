package com.web.pcdp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Title: MyConfiguration.java

 * Description:   过滤器配置

 * @author Guo_Jinhang

 * @date 2019年6月16日

 * @version 1.0
 */
@Configuration
public class MyConfiguration implements  WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(new com.web.pcdp.config.LoginInterceptor());
        registration.addPathPatterns("/**");         //所有路径都被拦截
        registration.excludePathPatterns("/","/login"
                ,"/register"
                ,"/Login"
                ,"/registerSubmit"
                ,"/getNextUserID"
                ,"/css/**"
                ,"/data/**"
                ,"/fonts/**"
                ,"/img/**"
                ,"/icons-reference/**"
                ,"/js/**"
                ,"/vendor/**");       //添加不拦截路径

    }

}
