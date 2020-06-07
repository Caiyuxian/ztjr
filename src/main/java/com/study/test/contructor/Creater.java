package com.study.test.contructor;

public interface Creater {

    public <T extends Product> T factory(Class<T> c);

}
