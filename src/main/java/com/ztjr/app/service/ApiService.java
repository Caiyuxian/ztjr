package com.ztjr.app.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztjr.dao.UserDao;
import com.ztjr.dao.UserExtInfoDao;
import com.ztjr.entity.User;
import com.ztjr.entity.UserAddrBook;
import com.ztjr.entity.UserExtInfo;
import com.ztjr.model.*;
import com.ztjr.utils.FileUtils;
import com.ztjr.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ApiService {

	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserExtInfoDao userExtDao;
	/**
	 * 密码登录
	 * @param phone
	 * @param pwd
	 */
	public Msg loginByPassword(String phone, String pwd) {
		//根据phone获取用户
		User u = userDao.getUserByPhone(phone);
		if(null == u) {
			return new Msg(Code.UserNoExist.getKey(), Code.UserNoExist.getValue());
		}
		//校验密码
		if(u.getPassword().equals(MD5.toMD5(pwd))) {
			Data data = new Data(Code.LoginSucc.getKey(), Code.LoginSucc.getValue());
			//每次登录都生成tokenId,暂时用用户ID+密码+当前时间的MD5作为tokenId
			String tokenId = MD5.toMD5(""+u.getId()+pwd+format.format(Calendar.getInstance().getTime()));
			data.put("accesstoken", tokenId);
			//更新用户token
			userDao.updateToken(u.getId(), tokenId);
			return data;
		}else {
			return new Msg(Code.PwdError.getKey(), Code.PwdError.getValue());
		}
	}

	/**
	 * 验证码登录
	 * @param phone
	 * @param pwd
	 */
	public Msg loginByCode(String phone, String pwd) {
		//根据phone获取用户
		User u = userDao.getUserByPhone(phone);
		if(null == u) {
			return new Msg(Code.UserNoExist.getKey(), Code.UserNoExist.getValue());
		}
		//校验验证码
		Cache cache = Cache.getInstance();
		IdentifyCode iden = cache.get(phone);
		if(iden == null) {
			return new Msg(Code.SysError.getKey(), Code.SysError.getValue());
		}
		//验证验证码，有效时间五分钟
		boolean isVerify = iden.isFit(new IdentifyCode(pwd, System.currentTimeMillis()));
		if(isVerify) {
			Data data = new Data(Code.LoginSucc.getKey(), Code.LoginSucc.getValue());
			//每次登录都生成tokenId,暂时用用户ID+密码+当前时间的MD5作为tokenId
			String tokenId = MD5.toMD5(""+u.getId()+u.getPassword()+format.format(Calendar.getInstance().getTime()));
			data.put("accesstoken", tokenId);
			//更新用户token
			userDao.updateToken(u.getId(), tokenId);
			return data;
		}else {
			return new Msg(Code.CodeError.getKey(), Code.CodeError.getValue());
		}
	}

	/**
	 * 注册/忘记密码
	 */
	public Msg regi(String type, String phone, String identifycode, String pwd) {
		Cache cache = Cache.getInstance();
		IdentifyCode iden = cache.get(phone);
		if(iden == null) {
			return new Msg(Code.SysError.getKey(), Code.SysError.getValue());
		}
		// 当前用户是否注册过
		boolean haveUser = userDao.isExist(phone);
        //验证验证码，有效时间五分钟
        boolean isVerify = iden.isFit(new IdentifyCode(identifycode, System.currentTimeMillis()));
        if ("1".equals(type)) { // 忘记密码
            // 没有注册过返回没有注册
            if(!haveUser) {
                return new Msg(Code.UserNoExist.getKey(), Code.UserNoExist.getValue());
            }

            if (isVerify) {
                userDao.updateUserPsw(phone, MD5.toMD5(pwd));
                return new Msg(Code.ForgetPswSucc.getKey(), Code.ForgetPswSucc.getValue());
            } else {
                return new Msg(Code.CodeError.getKey(), Code.CodeError.getValue());
            }
        } else{ // 注册
	    	//验证手机有没被注册过
		    if(haveUser) {
		    	return new Msg(Code.RegiFail.getKey(), Code.RegiFail.getValue());
		    }

		    if(isVerify) {
		    	User u = new User();
		    	u.setCreatetime( new Timestamp(System.currentTimeMillis()));
		    	u.setPassword(MD5.toMD5(pwd));
		    	u.setPhone(phone);
		    	userDao.regiSave(u);
		    	return new Msg(Code.RegiSucc.getKey(), Code.RegiSucc.getValue());
		    }else {
		    	return new Msg(Code.CodeError.getKey(), Code.CodeError.getValue());
		    }
        }
	}

	public Msg sendCode(String phone){
		//验证码发送逻辑
//		String code = SendIdentifyCode.getCode();
		String code = "6666";
		System.out.println(code);
		Cache cache = Cache.getInstance();
		cache.put(phone, new IdentifyCode(code, System.currentTimeMillis()));
//		SendIdentifyCode.send(code, phone);
		Msg data = new Msg(Code.SendCodeSucc.getKey(), Code.SendCodeSucc.getValue());
		return data;
	}

	/**
	 * 校验token.正确返回用户ID，失败返回0
	 * @param token
	 * @return
	 */
	public int checkTokenId(String token) {
		User u = userDao.getUserByTokenId(token);
		return null == u ? 0 : u.getId();
	}

	/**
	 * 保存用户其他信息
	 * @param phone
	 * @param idcard
	 * @param addr
	 * @param workAddr
	 * @param picture
	 * @param emergencyContact
	 * @param contact
	 * @param session
	 * @param userid
	 * @return
	 */
	public Msg saveUserExtInfo(String phone, String idCardName, String idcard, String addr, String workAddr, String picture,
			String emergencyContact, String contact, HttpSession session,String company,String alipay_score, int userid) {
		//先根据userId获取UserExtInfo对象，如果存在则为更新操作
		UserExtInfo ext = userExtDao.getExtInfo(userid);
		if(null == ext){
			ext = new UserExtInfo();
		}
		ext.setUserId(userid);
		ext.setIdCardName(idCardName);
		ext.setIdcard(idcard);
		ext.setCompany(company);
		ext.setAlipay_score(alipay_score);
		ext.setPhone(phone);
		ext.setAddress(addr);
		ext.setWorkAddr(workAddr);
		//紧急联系人
		JSONObject em =  (JSONObject) JSON.parse(emergencyContact);
		if (em != null) {
			ext.setCt_user1(em.getString("contact1"));
			ext.setCt_phone1(em.getString("phone1"));
			ext.setCt_user2(em.getString("contact2"));
			ext.setCt_phone2(em.getString("phone2"));
			ext.setCt_user3(em.getString("contact3"));
			ext.setCt_phone3(em.getString("phone3"));
		}
		//解析图片，base64转为图片
		JSONArray pt = JSON.parseArray(picture);
		if (pt != null) {
			String base64_1 = ((JSONObject)pt.get(0)).getString("value");
			String base64_2 = ((JSONObject)pt.get(1)).getString("value");
			String base64_3 = ((JSONObject)pt.get(2)).getString("value");
//			String base64_1 = FileUtils.imageToBase64("C:\\test\\f.jpg");
//			String base64_2 = FileUtils.imageToBase64("C:\\test\\f.jpg");
//			String base64_3 = FileUtils.imageToBase64("C:\\test\\f.jpg");
			//TODO 如果是重新上传的，那么把旧的图片删掉。
			//图片上传路径
			String uploadPath = session.getServletContext().getRealPath("/upload");
			String imageName1 = uploadPath+File.separator+FileUtils.newFileName()+"_1.jpg";
			String imageName2 = uploadPath+File.separator+FileUtils.newFileName()+"_2.jpg";
			String imageName3 = uploadPath+File.separator+FileUtils.newFileName()+"_3.jpg";
			FileUtils.base64ToImage(base64_1, imageName1);
			FileUtils.base64ToImage(base64_2, imageName2);
			FileUtils.base64ToImage(base64_3, imageName3);
			//保存相对路径即可
			imageName1 = imageName1.substring(imageName1.indexOf("upload"));
			imageName2 = imageName2.substring(imageName2.indexOf("upload"));
			imageName3 = imageName3.substring(imageName3.indexOf("upload"));
			ext.setIdCardRight(imageName1);
			ext.setIdCardBack(imageName2);
			ext.setIdCardByHand(imageName3);
		}
		userExtDao.saveUserExtInfo(ext);
		//解析联系人
		JSONArray ct = JSON.parseArray(contact);
		List<UserAddrBook> ctlist = new ArrayList<>();
		UserAddrBook udb = null;
		for(Object o : ct) {
			JSONObject ctO = (JSONObject) o;
			udb = new UserAddrBook(userid);
			udb.setPhone(ctO.getString("phone"));
			udb.setUsername(ctO.getString("name"));
			ctlist.add(udb);
		}
		//保存联系人
		if(ext.getId() != 0){
			//如果是重新上传的时候把数据库旧的记录先删了
			userExtDao.delContactAddrBook(userid);
		}
		userExtDao.saveContactAddrBook(ctlist);
		Data data = new Data(Code.PostInfoSucc.getKey(), Code.PostInfoSucc.getValue());
		return data;
	}
	/**
	 * 获取用户信息
	 */
	public Msg getUserInfo(int userId) {
		UserExtInfo info = userExtDao.getExtInfo(userId);
		Data data = new Data(Code.RequestSucc.getKey(),"");
		if (info != null) {
		    data.put("phone", info.getPhone());
		    data.put("idcardname", info.getIdCardName());
		    data.put("idcard", info.getIdcard());
		    data.put("alipay_score", info.getAlipay_score());
		    data.put("addr", info.getAddress());
		    data.put("company", info.getCompany());
		    data.put("workAddr", info.getWorkAddr());
		    data.put("contact1", info.getCt_user1());
		    data.put("contact2", info.getCt_user2());
		    data.put("contact3", info.getCt_user3());
		    data.put("phone1", info.getCt_phone1());
		    data.put("phone2", info.getCt_phone2());
		    data.put("phone3", info.getCt_phone3());
		}
		return data;
	}

}
