package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/7/2017.
 */

public class PiranhaPlantThread extends Thread {
    GameView gameView;
    public PiranhaPlantThread(GameView cv){
        gameView = cv;
    }
    private int delaytime=1000;

    public void delay(int time){
        try{
            sleep(time);
        }
        catch(InterruptedException e) {
            System.out.println("Exception occured");
        }
    }

    public void run(){
        PiranhaPlant piranhaPlant[] = gameView.piranhaPlant;
        while(gameView.hero.lives>0) {
            for (PiranhaPlant i : piranhaPlant) {
                if ((i.alive)&&(!i.hide)){
                    i.onMap(gameView.mapblocks);
                }
            }
            delay(delaytime);
            int hero_x=gameView.hero.x;
            for (PiranhaPlant i : piranhaPlant) {
                System.out.println(delaytime);
                if ((hero_x==i.x)&&(i.hide&&i.alive)){
                    i.move(0,-1);   //Show and Kill
                    break;
                }
                if (i.hide&&i.alive){
                    if ((hero_x-i.x>3)||(hero_x-i.x<-3)){   //Far away
                        i.move(0,-1);    //Show
                    }
                }
                else if ((!i.hide)&&i.alive){
                    i.disMap(gameView.mapblocks);
                    i.move(0,1);    //Hide
                }
            }
        }
        System.out.println("PiranhaPlant Thread Stop");
        return;
    }
}
