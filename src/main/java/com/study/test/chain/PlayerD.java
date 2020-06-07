package com.study.test.chain;

public class PlayerD extends Player {

    public PlayerD(Player player){
        this.setSuccessor(player);
    }

    @Override
    public void handle(int i) {
        if(i == 4){
            System.out.println("playerD 喝酒");
        } else{
            System.out.println("当前位循环最后一个");
            next(i);
        }
    }
}
