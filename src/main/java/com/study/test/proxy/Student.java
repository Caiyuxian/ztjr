package com.study.test.proxy;

public class Student implements Person {

    @Override
    public void learn() {
        System.out.println("I am a student...");
        System.out.println("so my first thing is studying...");
        System.out.println("to be a useful person...");
    }
}
