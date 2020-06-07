package com.study.test;

public class VolatileTest {

    /**
     *   volatile只是保证数据的可见性，并不能保证线程安全，
     *   当内存的值被更新时还是可能多个线程拿到相同的值
     *   下面的例子跑出来的结果就不是预期的10000.
     *   如果要保证count的数值正确可以通过加锁
     *   sychronized(VolatileTest.class){
     *       count++
     *   }
     */
    private volatile static int count = 0;

    public static void main(String args[]){
        for(int i=0; i<100;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0; j<100;j++){
                        count++;
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }


}
