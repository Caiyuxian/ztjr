package com.ztjr.model;

public enum Code {

	RequestSucc("200", "请求成功"),
	LoginSucc("200","登录成功"),
	SendCodeSucc("200","验证码发送成功"),
	SysError("500","系统异常,验证码信息不存在，请重新获取验证码"),
	RegiSucc("200","注册成功"),
	RegiFail("201","手机号已注册过"),
	ForgetPswSucc("200", "密码修改成功"),
	PostInfoSucc("200","资料上传成功"),
	PwdError("301","密码错误"),
	CodeError("302","验证码错误或已过期"),
	UserNoExist("303","用户不存在"),
	LoginTypeError("303","登录方式错误"),
	TokenError("304","TokenId不正确");
	
	private String key;
	private String value;
	
	Code(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getValue() {
		return this.value;
	}
}
