package edu.uci.github.mario;
import android.icu.text.SymbolTable;

import java.lang.Thread;

/**
 * Created by fxuyi on 6/4/2017.
 */

public class GravityThread extends Thread {
    GameView gameView;
    int delaytime=650;
    public GravityThread(GameView cv){
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
        while(gameView.hero.lives>0) {
            if  (gameView.mapblocks[gameView.hero.x][gameView.hero.y+1].visitible!=null){//Jump Onto
                if (gameView.mapblocks[gameView.hero.x][gameView.hero.y+1].visitible.Killible()){
                    gameView.mapblocks[gameView.hero.x][gameView.hero.y+1].visitible.jumpon(gameView.mapblocks);
                    gameView.killmark=1;
                }
            }

            if (gameView.mapblocks[gameView.hero.x][gameView.hero.y+1].passible){
                //Drop 1 block
                gameView.hero.Move(0,1,gameView.mapblocks,gameView.heroblocks);
                delaytime-=150;
                //Visit GameObject if possible
                if (gameView.mapblocks[gameView.hero.x][gameView.hero.y].visitible!=null){
                    System.out.println("Visit Happen");
                    gameView.mapblocks[gameView.hero.x][gameView.hero.y].visitible.visit(gameView.hero,gameView.mapblocks);
                }
            }
            else {
                gameView.JumpLocker=false;  //Can start a new jump
                delaytime=650;
            }

            //Delay
            delay(delaytime);
        }
        System.out.println("Gravity Thread Stop");
        return;
    }
}
