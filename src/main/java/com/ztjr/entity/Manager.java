package com.ztjr.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Manager {
	private int id;
	private String username;
	private String password;
	/**
	 * 角色：1、管理员 2、老板 3、客服
	 */
	private String role;
	private Timestamp createtime;
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
