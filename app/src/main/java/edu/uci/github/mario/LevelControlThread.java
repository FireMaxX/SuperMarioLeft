package edu.uci.github.mario;

import java.util.Currency;

/**
 * Created by fxuyi on 6/8/2017.
 */

//This thread will check and start a new level if mario touch the flag
public class LevelControlThread extends Thread {
    GameView gameView;
    public LevelControlThread(GameView cv){
        gameView = cv;
    }
    public void resetPointer(){
        gameView.coin=null;
        gameView.hide_coin=null;
        gameView.mushroom=null;
        gameView.hide_mushroom=null;
        gameView.fireFlower=null;
        gameView.hide_fireFlower=null;
        gameView.buzzyBeetle=null;
        gameView.bloober=null;
        gameView.piranhaPlant=null;
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
        while(true) {
            if (gameView.hero.lives<-1){ //Touch the flag
                if (gameView.CurrentLevel<3){
                    delay(1100);
                    gameView.hero.lives+=9;
                    gameView.hurtmark=0;
                    resetPointer();
                    gameView.CurrentLevel++;
                    gameView.startNewlevel(gameView.CurrentLevel);
                }
                else {
                    //Winner!
                    gameView.hero.lives=0;
                    gameView.hero.x=GlobleConstant.MapLength-10;
                    gameView.hero.y=3;
                    gameView.hurtmark=0;
                    gameView.killmark=0;
                    gameView.CurrentLevel=-1;
                    break;
                }
            }
            else if (gameView.hero.lives==0){
                //GameOver!
                gameView.CurrentLevel=0;
                break;
            }
        }
    }
}
