package com.ztjr.model;

public enum PayStatus {

	YES("1", "是"),
	NO("0", "否");
	
	private String key;
	private String value;
	
	PayStatus(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public static PayStatus getStatus(String key) {
		for(PayStatus p : PayStatus.values()) {
			if(p.getKey().equals(key)) {
				return p;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}
