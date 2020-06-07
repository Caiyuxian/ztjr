package com.ztjr.entity;
/**
 * 用户通讯录信息
 * @author Administrator
 */
public class UserAddrBook {
	
	public UserAddrBook(int userId) {
		this.userId = userId;
	}
	
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 姓名
	 */
	private String username;
	/**
	 * 电话
	 */
	private String phone;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
