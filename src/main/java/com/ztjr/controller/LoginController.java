package com.ztjr.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ztjr.dao.ManagerDao;
import com.ztjr.entity.Manager;
import com.ztjr.utils.MD5;

@Controller
public class LoginController {

	@Autowired
	private ManagerDao managerDao;

	@ResponseBody
	@RequestMapping("/login.do")
	public String login(String username,
						String password, String role, HttpSession session) {
		Manager m = managerDao.getByName(username);
		if(m != null && m.getPassword().equals(MD5.toMD5(password))){
			session.setAttribute("admin", m);
			return "2";
		}else{
			//用户名或密码错误
			return "1";
		}
	}

	@RequestMapping("/main.do")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView("main");
		mv.addObject("user",session.getAttribute("admin"));
		return mv;
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.removeAttribute("admin");
		return "redirect:admin/login.jsp";
	}
	
}
