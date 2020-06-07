package com.study.test.proxy.Custom;

import com.study.test.proxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyStudentProxy implements MyInvocationHandler {

    private Person target;

    public Object getProxyInstance(Person person){
        this.target = person;
        Class clazz = target.getClass();
        return MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Proxy: to be a good student must to be a good person...");
        method.invoke(target, args);
        System.out.println("Proxy: this is a base rule to be a person...");
        return null;
    }
}
