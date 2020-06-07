package com.ztjr.entity;
/**
 * 用户额外信息
 * @author Administrator
 */
public class UserExtInfo {
	/**
	 * 主键
	 */
	private int id;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 用户ID
	 */
	private int userId;
	/**
	 * 身份证姓名
	 */
	private String idCardName;
	/**
	 * 身份证号码
	 */
	private String idcard;
	/**
	 * 居住地址
	 */
	private String address;
	/**
	 * 工作地址
	 */
	private String workAddr;
	/**
	 * 工作单位
	 */
	private String company;
	/**
	 * 芝麻分
	 */
	private String alipay_score;
	/**
	 * 身份证正面
	 */
	private String idCardRight;
	/**
	 * 身份证反面
	 */
	private String idCardBack;
	/**
	 * 手持身份证照
	 */
	private String idCardByHand;
	/**
	 * 紧急联系人1
	 */
	private String ct_user1;
	/**
	 * 紧急联系人电话1
	 */
	private String ct_phone1;
	/**
	 * 紧急联系人2
	 */
	private String ct_user2;
	/**
	 * 紧急联系人电话2
	 */
	private String ct_phone2;
	/**
	 * 紧急联系人3
	 */
	private String ct_user3;
	/**
	 * 紧急联系人电话3
	 */
	private String ct_phone3;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWorkAddr() {
		return workAddr;
	}
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}
	public String getIdCardRight() {
		return idCardRight;
	}
	public void setIdCardRight(String idCardRight) {
		this.idCardRight = idCardRight;
	}
	public String getIdCardBack() {
		return idCardBack;
	}
	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}
	public String getIdCardByHand() {
		return idCardByHand;
	}
	public void setIdCardByHand(String idCardByHand) {
		this.idCardByHand = idCardByHand;
	}
	public String getCt_user1() {
		return ct_user1;
	}
	public void setCt_user1(String ct_user1) {
		this.ct_user1 = ct_user1;
	}
	public String getCt_phone1() {
		return ct_phone1;
	}
	public void setCt_phone1(String ct_phone1) {
		this.ct_phone1 = ct_phone1;
	}
	public String getCt_user2() {
		return ct_user2;
	}
	public void setCt_user2(String ct_user2) {
		this.ct_user2 = ct_user2;
	}
	public String getCt_phone2() {
		return ct_phone2;
	}
	public void setCt_phone2(String ct_phone2) {
		this.ct_phone2 = ct_phone2;
	}
	public String getCt_user3() {
		return ct_user3;
	}
	public void setCt_user3(String ct_user3) {
		this.ct_user3 = ct_user3;
	}
	public String getCt_phone3() {
		return ct_phone3;
	}
	public void setCt_phone3(String ct_phone3) {
		this.ct_phone3 = ct_phone3;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	public String getAlipay_score() {
		return alipay_score;
	}

	public void setAlipay_score(String alipay_score) {
		this.alipay_score = alipay_score;
	}

	public String getIdCardName() {
		return idCardName;
	}

	public void setIdCardName(String idCardName) {
		this.idCardName = idCardName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
}
