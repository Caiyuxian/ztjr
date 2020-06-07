package com.study.test.chain;

public class PlayerC extends Player {

    public PlayerC(Player player){
        this.setSuccessor(player);
    }

    @Override
    public void handle(int i) {
        if(i == 3){
            System.out.println("playerC 喝酒");
        } else{
            System.out.println("传给D");
            next(i);
        }
    }
}
