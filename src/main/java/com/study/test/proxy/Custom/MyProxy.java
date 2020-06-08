package com.study.test.proxy.Custom;

import com.study.test.contructor.ContructCreator;
import com.study.test.proxy.Person;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Yuxian
 * @date 2020/6/7
 *
 * 代理类：主要是生成被代理对象的源代码，编译成class文件，然后重新动态加载到JVM中，返回一个新的代理对象
 */
public class MyProxy {

    private static final String ln = "\r\n";

    public static Object newProxyInstance(MyClassLoader loader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h){
        try{
            //1、根据原对象生成代理对象的源码Java文件
            String src = generateSrc(interfaces);
            String path = MyProxy.class.getResource("").getPath();
            File javaFile = new File(path, "$Proxy0.java");
            FileWriter fw = new FileWriter(javaFile);
            fw.write(src);
            fw.flush();
            fw.close();
            //2、根据源码编译成class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable file = manager.getJavaFileObjects(javaFile);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null,null, file);
            task.call();

            //3、动态将class文件加载到JVM中
            //4、返回代理对象实例
            Class proxyClass =  loader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
            return c.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer src = new StringBuffer();
        src.append("package com.study.test.proxy.Custom;" + ln);
        src.append("import java.lang.reflect.Method;" + ln);
        src.append("public class $Proxy0 implements " + resolveInterface(interfaces) + "{" + ln);
        src.append("MyInvocationHandler h;" + ln);
        src.append("public $Proxy0 (MyInvocationHandler h){" + ln);
        src.append("this.h = h;" + ln);
        src.append("}" + ln);
        for(Class c : interfaces){
            Method[] methods = c.getMethods();
            for(Method m : methods){
                src.append("public " + m.getReturnType() +" "+ m.getName() + "(){" + ln);
                src.append("try{" + ln);
                src.append("Method m = " + c.getName() + ".class.getMethod(\""+ m.getName() +"\",new Class[]{});" + ln);
                src.append("h.invoke(null, m, null);" + ln);
                src.append("}catch (Throwable e){" + ln);
                src.append(" e.printStackTrace();" + ln);
                src.append("}" + ln);
                src.append("}" + ln);
            }
        }
        src.append("}" + ln);
        return src.toString();
    }

    private static String resolveInterface(Class<?>[] interfaces){
        StringBuffer src = new StringBuffer();
        for(Class c : interfaces){
            src.append(c.getName() + ",");
        }
        return src.substring(0, src.length()-1).toString();
    }

}
