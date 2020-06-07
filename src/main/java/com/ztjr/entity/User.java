package com.ztjr.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 用户基本信息
 * @author Administrator
 */
public class User {
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * token
	 */
	private String token;
	/**
	 * 注册时间
	 */
	private Timestamp createtime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
