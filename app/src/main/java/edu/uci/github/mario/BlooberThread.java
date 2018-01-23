package edu.uci.github.mario;

import java.lang.Thread;
/**
 * Created by fxuyi on 6/7/2017.
 */

public class BlooberThread extends Thread{
    GameView gameView;
    private int direction=1;
    public BlooberThread(GameView cv){
        gameView = cv;
    }

    public void delay(int delaytime){
        try{
            sleep(delaytime);
        }
        catch(InterruptedException e) {
            System.out.println("Exception occured");
        }
    }
    public void run(){
        delay(500);
        Bloober bloober[]=gameView.bloober;
        while(gameView.hero.lives>0) {
            for (Bloober i:bloober){
                if (i.alive){
                    i.onMap(gameView.mapblocks);
                }
            }
            delay(800);
            for (Bloober i:bloober){
                if (i.alive){
                    i.disMap(gameView.mapblocks);
                    int hero_x=gameView.hero.x;
                    if (hero_x>i.x){direction=1;}
                    else if(hero_x<=i.x){direction=-1;}
                    int next=i.x+direction;
                    if ((next<0)||(next>GlobleConstant.MapLength)){
                        direction*=-1;
                        next=i.x+direction;
                    }
                    if (!gameView.mapblocks[next][i.y].passible){
                        direction*=-1;
                    }
                    i.move(direction,0);
                }
            }
        }
        System.out.println("Bloober Thread Stop");
        return;
    }
}
