package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/7/2017.
 */

//This thread is in charge of redrawing any item(coin, mushroom, fireflower) if they were covered by enemies  on canvas
public class ItemRedrawThread extends Thread {
    GameView gameView;
    public ItemRedrawThread(GameView cv){
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
        delay(1000);
        Coin coin[]=gameView.coin;
        Mushroom mushroom[]=gameView.mushroom;
        FireFlower fireFlower[]= gameView.fireFlower;
        while(gameView.hero.lives>0){
            for (Coin i:coin){
                if (i.checkExist()){
                    i.onMap(gameView.mapblocks);
                }
            }
            if (fireFlower!=null){
                for (FireFlower i:fireFlower){
                    if (i.checkExist()){
                        i.onMap(gameView.mapblocks);
                    }
                }
            }
            if (mushroom!=null){
                for (Mushroom i:mushroom){
                    if (i.checkExist()){
                        i.onMap(gameView.mapblocks);
                    }
                }
            }
            if (gameView.endPointFlag.checkExist()){
                gameView.endPointFlag.onMap(gameView.mapblocks);
            }
            delay(200);
        }
        System.out.println("ItemRedraw Thread Stop");
        return;
    }
}
