package com.study.test.chain;

public abstract class Player {
    private Player successor;

    public abstract void handle(int i);

    protected void setSuccessor(Player player){
        this.successor =player;
    }

    public void next(int i){
        if(successor == null){
            System.out.println("game is over");
        } else{
            successor.handle(i);
        }
    }
}
