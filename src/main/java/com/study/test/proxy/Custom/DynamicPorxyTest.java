package com.study.test.proxy.Custom;

import com.study.test.proxy.Person;
import com.study.test.proxy.Student;

public class DynamicPorxyTest {
    public static void main(String[] args) {
        Person p = new Student();
        Person proxy = (Person) new MyStudentProxy().getProxyInstance(p);
        System.out.println(proxy);
        proxy.learn();
    }
}
