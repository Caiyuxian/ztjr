package com.study.test;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {

    public static void main(String[] agrs) throws Exception{
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);

        Unsafe unsafe = (Unsafe) f.get(null);

        Player p = (Player) unsafe.allocateInstance(Player.class);
        p.setAge(18);
        p.setName("Tom");
        System.out.println(p);
    }

}

class Player{
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Age:"+age+",Name:"+name;
    }
}
