package com.ztjr.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ztjr.dao.ManagerDao;
import com.ztjr.entity.Manager;
import com.ztjr.utils.MD5;

@Controller
@RequestMapping("/admin")
public class ManagerController {
	
	@Autowired
	private ManagerDao managerDao;

	@RequestMapping("/showmanager.do")
	public ModelAndView showMessage(@RequestParam(value="name", required=false, defaultValue="spring") String name) {
		ModelAndView mv = new ModelAndView("html/adminuser");
		mv.addObject("message", "");
		mv.addObject("name", name);
		return mv; 
	}
	
	@RequestMapping("/addmanager.do")
	@ResponseBody
	public String save(String username, String password, String role) {
		boolean exist = managerDao.isExist(username);
		if(exist) {
			return "2";
		}
		Manager m = new Manager();
		m.setCreatetime(new Timestamp(System.currentTimeMillis()));
		m.setPassword(MD5.toMD5(password));
		m.setUsername(username);
		m.setRole(role);
		int i = managerDao.saveManager(m);
		return i==0 ? "0": "1";
	}
	
	@ResponseBody
	@RequestMapping("/changepwd.do")
	public String changePwd(String pwdold, String pwdnew, HttpSession session) {
		Manager m = (Manager)session.getAttribute("admin");
		if(!MD5.toMD5(pwdold).equals(m.getPassword())) {
			return "1";
		}else {
			managerDao.updatePwd(m.getId(), pwdnew);
			session.removeAttribute("admin");
			return "2";
		}
	}
	
	@RequestMapping("/getAllManager.do")
	@ResponseBody
	public String getAllManager(int page, int rows) {
		List<Manager> list = managerDao.getByPage(rows, page, null);
		int count = managerDao.count("t_manager");
		Map<String, Object> map = new HashMap<>();
		map.put("total", count);
		map.put("rows", list);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/resetpwd.do")
	@ResponseBody
	public String resetPassword(String password,int userid) {
		managerDao.updatePwd(userid, password);
		return "1";
	}
}
