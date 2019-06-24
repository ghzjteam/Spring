package com.web.pcdp.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Title: AuthInterceptor.java

 * Description:   拦截器配置

 * @author Guo_Jinhang

 * @date 2019年6月16日

 * @version 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if( session.getAttribute("login_user")==null) {
            response.sendRedirect("/login");
            return false;
        }else
            return true;
    }

}
