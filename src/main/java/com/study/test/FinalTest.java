package com.study.test;

public class FinalTest {
    // //实例变量：必要要在非静态初始化块，声明该实例变量或者在构造器中指定初始值，而且只能在这三个地方进行指定。
    private final int a;
    {
        a = 0;
    }
    private final int b = 0;
    private final int c ;

    public FinalTest(){
        c = 0;
    }

    //final修饰的实例变量：必须要在静态初始化块中指定初始值或者声明该类变量时指定初始值，而且只能在这两个地方之一进行指定；
    private static final int d = 0;
    private static final int e;
    static {
        e = 0;
    }

    //1. 父类的final方法是不能够被子类重写的
    //2. final方法是可以被重载的
    //3. 当一个类被final修饰时，表名该类是不能被子类继承的
}
