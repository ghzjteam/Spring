package com.web.pcdp.service.fileter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.pcdp.entity.Users;

public class SysInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("��������������֤���ҽ�����");
		HttpSession session = request.getSession();
		Users userInfo = (Users) session.getAttribute("userInfo");
		if (userInfo == null) {
			System.out.println("��֤���û�û�е�¼");
			response.sendRedirect(request.getContextPath() + "/login.html");
			return false;
		}
		System.out.println("��֤���û��Ѿ���¼");
		return true;
	}

	/**
	 * ������ͼʱִ�У��������������쳣������¼����־��
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2,
			Exception exception) {
//-----------------//
	}

	/**
	 * - ������ͼ֮ǰִ�У������޸�ModelAndView
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
//----------------------------//
	}
}
