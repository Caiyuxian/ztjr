package com.ztjr.model;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	
	private static final Cache cache = new Cache();
	
	public static Cache getInstance(){
		return cache;
	}
	
	private Cache() {}
	
	public Map<String, IdentifyCode> map = new HashMap<>();
	
	public void put(String key, IdentifyCode value) {
		map.put(key, value);
	}
	
	public IdentifyCode get(String key) {
		return map.get(key);
	}

}
