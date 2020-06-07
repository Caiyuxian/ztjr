package com.study.test.contructor;

public class FactoryTest {

    public static void  main(String[] args){
        Creater c = new ContructCreator();
        ConcreteProduct p = c.factory(ConcreteProduct.class);
        p.product();
    }
}
