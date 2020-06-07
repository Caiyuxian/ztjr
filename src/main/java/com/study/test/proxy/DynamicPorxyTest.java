package com.study.test.proxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

public class DynamicPorxyTest {
    public static void main(String[] args) {
        Person p = new Student();
        Person proxy = (Person) new StudentProxy().getProxyInstance(p);
        proxy.learn();
    }
}
