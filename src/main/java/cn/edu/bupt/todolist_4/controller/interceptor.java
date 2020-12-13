package cn.edu.bupt.todolist_4.controller;//package hw4.management.controller;
//
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class interceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //一旦登录，session中就会有login
        Object user = request.getSession().getAttribute("login");
        if(user == null)
        {
            //未登录，返回登录页面
//            request.setAttribute("msg","没有权限请先登录");
            request.getRequestDispatcher("/login").forward(request,response);
            return false;
        }
        else {
            //已登录，放行请求
            return true;
        }

    }


}
