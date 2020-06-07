package com.study.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    public static void main(String args[]){
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 6; i++) {
            Thread t = new Thread(()->{
                try {
                    lock.lock();
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            });
            t.start();
        }
    }
}
