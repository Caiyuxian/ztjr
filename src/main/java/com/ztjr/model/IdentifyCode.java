package com.ztjr.model;

public class IdentifyCode {

	private String code;
	private long time;
	
	public IdentifyCode(String code, long time) {
		super();
		this.code = code;
		this.time = time;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean isFit(IdentifyCode identifyCode) {
		if(this.code.equalsIgnoreCase(identifyCode.getCode()) && ((identifyCode.getTime()-this.getTime()) < 5*60*1000)) {
			return true;
		}
		return false;
	}
	
}
