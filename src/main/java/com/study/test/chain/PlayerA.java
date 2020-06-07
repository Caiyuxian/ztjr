package com.study.test.chain;

public class PlayerA extends Player {

    public PlayerA(Player player){
        this.setSuccessor(player);
    }

    @Override
    public void handle(int i) {
        if(i == 1){
            System.out.println("playerA 喝酒");
        } else{
            System.out.println("传给B");
            next(i);
        }
    }
}
