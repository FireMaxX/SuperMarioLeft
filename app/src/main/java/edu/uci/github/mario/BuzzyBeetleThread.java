package edu.uci.github.mario;

import java.lang.Thread;

/**
 * Created by fxuyi on 6/6/2017.
 */

public class BuzzyBeetleThread extends Thread {
    GameView gameView;
    private int direction=1;
    private int counter=0;
    private int delaytime=1000;
    public BuzzyBeetleThread(GameView cv){
        gameView = cv;
    }

    public void delay(int time){
        try{
            sleep(time);
        }
        catch(InterruptedException e) {
            System.out.println("Exception occured");
        }
    }
    public void run(){
        delay(500);
        BuzzyBeetle buzzyBeetle[]=gameView.buzzyBeetle;
        while(gameView.hero.lives>0) {
            for (BuzzyBeetle i:buzzyBeetle){
                if ((i.alive)&&(!i.defend)){
                    i.onMap(gameView.mapblocks);
                    gameView.cameraSwitch();
                }
            }
            delay(delaytime);
            for (BuzzyBeetle i:buzzyBeetle){
                if ((i.alive)&&(!i.defend)){
                    i.disMap(gameView.mapblocks);
                    int next=i.x+direction;
                    if ((next<0)||(next>GlobleConstant.MapLength)){
                        direction*=-1;
                        next=i.x+direction;
                        this.counter=0;
                    }
                    if ((counter>5)||(!gameView.mapblocks[next][i.y].passible)){
                        direction*=-1;
                        this.counter=0;
                    }
                    else{
                        this.counter++;
                    }
                    i.move(direction,0);
                }
            }
        }
        System.out.println("BuzzyBeetle Thread Stop");
        return;
    }
}
