package com.ztjr.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ztjr.entity.Manager;

public class LoginFilter implements Filter {

	private static List<String> list = new ArrayList<>();
	static {
		//老板才有权的操作
		list.add("loan.do");
		list.add("payback.do");
		list.add("reaudit.do");
	}
	
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        //拿到登录对象
        Manager m = new Manager();
        m = (Manager)session.getAttribute("admin");
        //获取请求地址
        String reqUrl = req.getRequestURI();
        if(reqUrl.contains("/api")){
            chain.doFilter(req, resp);
        }else{
            String url[] = reqUrl.split("/");
            String ac = url[url.length-1];
            //登录页面和登录请求操作不用过滤
            if(ac.startsWith("login")){
                chain.doFilter(req, resp);
                return;
            }
            //判断是否已经登录，若是没有登录返回登录页面
            if(null == m){
                resp.sendRedirect("admin/login.jsp");
            }else if(!m.getRole().equals("2") && list.contains(ac)){//只能老板操作
                resp.sendRedirect("admin/login.jsp");
            }else if(!m.getRole().equals("1") && ac.equals("resetpwd.do")){//只要管理员操作
                resp.sendRedirect("admin/login.jsp");
            }else {
                chain.doFilter(req, resp);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
