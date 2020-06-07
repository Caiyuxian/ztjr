package com.study.test.chain;

public class PlayerB extends Player {

    public PlayerB(Player player){
        this.setSuccessor(player);
    }

    @Override
    public void handle(int i) {
        if(i == 2){
            System.out.println("playerB 喝酒");
        } else{
            System.out.println("传给C");
            next(i);
        }
    }
}
