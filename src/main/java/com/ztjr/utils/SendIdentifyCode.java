package com.ztjr.utils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

public class SendIdentifyCode {

//	private static String url = "http://dingxin.market.alicloudapi.com/dx/sendSms";
	private static String host = "http://dingxin.market.alicloudapi.com";
	private static String path = "/dx/sendSms";
//	private static String appKey = "f58e783b05afad233f80a14957f92e23";
	private static String appCode ="535041e046dc4810b9c7b30f665f0101";
	private static String method = "POST";
	
	public static String getCode() {
		String str = "0123456789";
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < 4; i++) {
			int round = (int)(Math.random()*10);
			sb.append(str.charAt(round));
		}
		return sb.toString();
	}
	
	/**
	 * 发送成功{"return_code": "00000", "order_id": "ALY15........825"}
	 * 发送失败{"return_code": "10000"}
	 */
	public static void send(String code, String phone) {
		Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appCode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phone);
	    querys.put("param", "code:"+code);
	    querys.put("tpl_id", "TP1711063");
	    Map<String, String> bodys = new HashMap<String, String>();
	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	//TODO 记录日志
	    	e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
//		send(getCode(),"15602964176");
		System.out.println(new Timestamp(System.currentTimeMillis()));
	}
}
