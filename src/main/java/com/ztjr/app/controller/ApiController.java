package com.ztjr.app.controller;

import com.alibaba.fastjson.JSON;
import com.ztjr.app.service.ApiService;
import com.ztjr.app.service.LoanBillService;
import com.ztjr.model.Code;
import com.ztjr.model.Msg;
import com.ztjr.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ApiService apiService;
	@Autowired
	private LoanBillService billService;

	/**
	 * 登录接口
	 * @param loginType	1代表密码登录 2代表验证码登录
	 * @param phone 手机号码
	 * @param pwd 密码或验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login.do")
	public Msg login(String loginType, String phone, String pwd) {
		if("1".equals(loginType)) {
			//密码登录逻辑
			return apiService.loginByPassword(phone, pwd);
		}else if("2".equals(loginType)) {
			//验证码登录逻辑
			return apiService.loginByCode(phone, pwd);
		}else {
			//异常情况
			Msg data = new Msg(Code.LoginTypeError.getKey(), Code.LoginTypeError.getValue());
			return data;
		}
	}

	/**
	 * 注册接口
	 * @param phone 手机号码
	 * @param identifycode 验证码
	 * @param pwd 密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register.do")
	public Msg register(String type, String phone, String identifycode, String pwd) {
		//注册逻辑
		return apiService.regi(type, phone, identifycode, pwd);
	}

	/**
	 * 获取验证码
	 * @param phone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCode.do", method = RequestMethod.POST)
	public Msg getIdetifyCode(String phone) {
		return apiService.sendCode(phone);
	}

	/**
	 * 获取资料
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUserInfo.do")
	public Msg getUserInfo(String token) {
		//验证tokenId
		int userid = apiService.checkTokenId(token);
		if(userid == 0)
			return new Msg(Code.TokenError.getKey(), Code.TokenError.getValue());
		return apiService.getUserInfo(userid);
	}

	/**
	 * 上传资料
	 * @param token	TokenId
	 * @param phone	手机号码
	 * @param idcard 身份证
	 * @param addr 常住地址
	 * @param workAddr 工作地址
	 * @param picture 图片json串	 {"picture":[{"value":"dafdafadf"},{"value":"dafdafadf"}}
	 * @param emergencyContact 紧急联系人 {"emergencycontact":[{"name":"张三","phone":"123"},{"name":"张三","phone":"123"}]}
	 * @param contact 联系人 {"contact":[{"name":"张三","phone":"123"},{"name":"张三","phone":"123"}]}
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/postInfo.do")
	public Msg postUserInfo(@RequestParam(required=false,defaultValue="123")String token,
			@RequestParam(required=false,defaultValue="0")String phone,
			@RequestParam(required = false, defaultValue = "")String idcardname,
			@RequestParam(required=false,defaultValue="0")String idcard,
			@RequestParam(required=false,defaultValue="0")String alipay_score,
			@RequestParam(required=false,defaultValue="123")String company,
			@RequestParam(required=false,defaultValue="123")String addr,
			@RequestParam(required=false,defaultValue="123")String workAddr,
			@RequestParam(required=false,defaultValue="[{\"value\":\"dafdafadf\"},{\"value\":\"dafdafadf\"},{\"value\":\"dafdafadf\"}]")String picture,
			@RequestParam(required=false,defaultValue="{\"contact1\":\"张三\",\"phone1\":\"123\",\"contact2\":\"张三\",\"phone2\":\"123\",\"contact3\":\"张三\",\"phone3\":\"123\"}")String emergencyContact,
			@RequestParam(required=false,defaultValue="[{\"name\":\"张三\",\"phone\":\"123\"},{\"name\":\"张三\",\"phone\":\"123\"}]")String contact,
							HttpSession session) {
		//资料上传逻辑
		//验证tokenId
		int userid = apiService.checkTokenId(token);
		if(userid == 0)
			return new Msg(Code.TokenError.getKey(), Code.TokenError.getValue());
		return apiService.saveUserExtInfo(phone, idcardname,idcard, addr, workAddr, picture, emergencyContact, contact, session,company,alipay_score, userid);
	}


	/**
	 * 上传单张图片
	 * @param picture
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadFile.do", method=RequestMethod.POST)
	public String uploadFile(@RequestParam(required=true) MultipartFile picture, HttpSession session) throws IOException {
		String uploadPath = session.getServletContext().getRealPath("/upload");
		FileUtils.saveFile(picture, uploadPath);
		return "success";
	}

	/**
	 * 上传图片数组
	 * @param picture
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadMultiFileByArray.do", method=RequestMethod.POST)
	public String uploadMultiFileByArray(@RequestParam(required=true) MultipartFile[] picture, HttpSession session) throws IOException {
		String uploadPath = session.getServletContext().getRealPath("/upload");
	    for(MultipartFile f : picture) {
	    	FileUtils.saveFile(f, uploadPath);
	    }
	    return JSON.toJSONString("success");
	}

	/**
	 * 上传三张图片，三个参数接收
	 * @param idcardright
	 * @param idcardback
	 * @param idcardbyhand
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/uploadMultiFile.do", method=RequestMethod.POST)
	public String uploadMultiFile(@RequestParam(required=true) MultipartFile idcardright,
			@RequestParam(required=true) MultipartFile idcardback,
			@RequestParam(required=true) MultipartFile idcardbyhand,
			HttpSession session) throws IOException {
		String uploadPath = session.getServletContext().getRealPath("/upload");
		FileUtils.saveFile(idcardright, uploadPath);
		FileUtils.saveFile(idcardback, uploadPath);
		FileUtils.saveFile(idcardbyhand, uploadPath);
		return JSON.toJSONString("success");
	}

	@RequestMapping(value="/sayhi.do")
	public ModelAndView sayHi(@RequestParam(required=true) String name,
			HttpSession session) throws IOException {
		ModelAndView mv = new ModelAndView("hellospring");
		mv.addObject("name", name);
		mv.addObject("message","dee");
		return mv;
	}

	/**
	 * 发起借款
	 * @param cycle 周期类型
	 * @param amount 金额
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/loan.do")
	public Msg loan(String amount, String cycle, String token) {
		int userid = apiService.checkTokenId(token);
		if(userid == 0)
			return new Msg(Code.TokenError.getKey(), Code.TokenError.getValue());
		return billService.requestLoan(amount, cycle, userid);
	}

	/**
	 * 查看我的进度
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkProcess.do")
	public Msg checkProcess(String token) {
		int userid = apiService.checkTokenId(token);
		if(userid == 0)
			return new Msg(Code.TokenError.getKey(), Code.TokenError.getValue());
		return billService.checkProcess(userid);
	}
}
