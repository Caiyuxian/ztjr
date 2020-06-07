package com.ztjr.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ztjr.dao.UserAddrBookDao;
import com.ztjr.dao.UserDao;
import com.ztjr.dao.UserExtInfoDao;
import com.ztjr.entity.User;
import com.ztjr.entity.UserAddrBook;
import com.ztjr.entity.UserExtInfo;
import com.ztjr.utils.ExcelUtils;

@Controller
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserExtInfoDao extInfoDao;
	@Autowired
	private UserAddrBookDao bookDao;
	
	@RequestMapping("/getuserlist.do")
	@ResponseBody
	public String getUserList(int page, int rows, String phone) {
		String whereSql = null;
		if(!StringUtils.isEmpty(phone)){
			whereSql = " phone='"+phone+"'";
		}
		List<User> list = userDao.getByPage(rows, page, whereSql);
		int count = userDao.count("t_user");
		Map<String, Object> map = new HashMap<>();
		map.put("total", count);
		map.put("rows", list);
		return JSON.toJSONString(map);
	}
	
	@RequestMapping("/showuser.do")
	@ResponseBody
	public ModelAndView showUser() {
		ModelAndView mv = new ModelAndView("html/userlist");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public UserExtInfo getUserextinfo(int userId) {
		return extInfoDao.getExtInfo(userId);
	}
	
	@RequestMapping("/export.do")
	@ResponseBody
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, int userid) {
		List<UserAddrBook> userBook = bookDao.getAllContact(userid);
		int size = userBook.size();
		String[][] value = new String[size][2];
		int i = 0;
		for(UserAddrBook b : userBook) {
			value[i][0] = b.getUsername();
			value[i][1] = b.getPhone();
			i++;
		}
		String[] title = {"姓名","联系方式"};
		String sheetName = "通讯录";
		String fileName = "通讯录"+System.currentTimeMillis()+".xls";
		
		HSSFWorkbook wb = ExcelUtils.geneExcel(sheetName, title, value);
		//响应到客户端
		this.setResponseHeader(response, fileName);
		OutputStream os;
		try {
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
            try {
                fileName = new String(fileName.getBytes(),"UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=UTF8");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Origin", "*");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
