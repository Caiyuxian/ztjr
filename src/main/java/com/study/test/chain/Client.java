package com.study.test.chain;

public class Client {
    /**
     * 责任链模式，每个类处理一种情况，当不满足当前条件时传递给下一个类进行处理
     * @param args
     */
    public static void main(String[] args){
        Player player = new PlayerA(new PlayerB(new PlayerC(new PlayerD(null))));
        player.handle(6);
    }
}
