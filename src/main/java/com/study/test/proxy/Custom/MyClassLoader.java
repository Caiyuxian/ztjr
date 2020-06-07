package com.study.test.proxy.Custom;

import java.io.*;

/**
 * 加载class类文件，装载进JVM中
 */
public class MyClassLoader extends ClassLoader {

    private File baseDir;

    public MyClassLoader() {
        String path = MyClassLoader.class.getResource("").getPath();
        baseDir = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File classFile = new File(baseDir, name.replaceAll("\\.", "/") + ".class");
        if(classFile.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try{
                in = new FileInputStream(classFile);
                out = new ByteArrayOutputStream();
                int len;
                byte[] buff = new byte[1024];
                while ((len = in.read(buff)) != -1){
                    out.write(buff, 0, len);
                }
                return defineClass(name, out.toByteArray(), 0, out.size());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(in != null){
                    try{
                        in.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try{
                        out.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
