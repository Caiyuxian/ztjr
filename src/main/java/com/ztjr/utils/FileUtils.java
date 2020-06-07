package com.ztjr.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	public static String newFileName(String name){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String newFileName = sf.format(Calendar.getInstance().getTime());
		String []path = name.split("\\.");
		newFileName = newFileName+"."+path[1];
		return newFileName;
	}
	
	public static String newFileName(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String newFileName = sf.format(Calendar.getInstance().getTime());
		return newFileName;
	}
	
	
	public static void saveFile(MultipartFile file, String uploadPath) throws IOException {
		//创建文件夹 
	    File dir=new File(uploadPath);
	    if(!dir.exists()){
	        dir.mkdirs();
	    }
    	String name = file.getOriginalFilename();
		File f = new File(dir, name);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(file.getBytes());
		fos.close();
	}

	/**
	 * Base64字节码转图片
	 * 
	 * @param base64Str 字节码存储路径
	 * @param path      文件存储路径
	 * @return 返回true或者false
	 */
	public static boolean base64ToImage(String base64String, String path) {
		if (null == base64String)
			return false;
		try {
			byte[] bytes = Base64.getDecoder().decode(base64String);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {
					bytes[i] += 256;
				}
			}
			File img = new File(path);
			if (!img.getParentFile().exists()) {
				img.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(path);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	 /**
     * 图片转为Base64字节码
     * @param path 图片路径
     * @return 返回base64字节码
     */
  public static String imageToBase64(String path) {
      byte[] data = null;
      try {
          InputStream in = new FileInputStream(path);
          data = new byte[in.available()];
          in.read(data);
          in.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return Base64.getEncoder().encodeToString(data);
  }


  public static void main(String[] args) throws IOException {
  	System.out.println(Base64.getEncoder().encodeToString(new String("HelloWorld").getBytes()));
	 /* String imagurl = "C:\\test\\1955.jpg";
	  String base64String = imageToBase64(imagurl);
	  try {
		FileWriter os = new FileWriter("C:\\test\\base64.txt");
		os.write(base64String, 0, base64String.length()-1);
		
		
		FileReader io = new FileReader("C:\\test\\base64.txt");
		StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(io);//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(result.toString());
        base64ToImage(result.toString(), "C:\\test2\\dmp3.png");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}*/
	  
//	  base64ToImage(base64String, "C:\\test2\\dmp2.png");
  }

}
