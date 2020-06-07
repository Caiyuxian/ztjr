package com.ztjr.model;

import java.util.HashMap;
import java.util.Map;

public class Data extends Msg {

	private Map<String, Object> data;
	
	public Data(String code, String msg) {
		super(code, msg);
	}
	
	public void put(String key, Object value) {
		if(null == data) {
			data = new HashMap<>();
		}
		data.put(key, value);
	}
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
