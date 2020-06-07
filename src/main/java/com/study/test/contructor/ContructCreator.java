package com.study.test.contructor;

public class ContructCreator implements Creater {

    @Override
    public <T extends Product> T factory(Class<T> c) {
        Product p = null;
        try {
            p = (Product) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T)p;
    }
}
