package edu.uci.github.mario;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import java.lang.Thread;
/**
 * Created by fxuyi on 6/4/2017.
 */

public class DrawThread extends Thread  {
    GameView gameView;
    private int counter=0;
    public DrawThread(GameView cv){
        gameView = cv;
    }

    //reDraw Canvas according to the current icon status
    public void  reDraw() {
        SurfaceHolder holder = gameView.getHolder();
        Canvas canvas = holder.lockCanvas();

        if (canvas != null) {
            gameView.draw(canvas);
            holder.unlockCanvasAndPost(canvas);
        }
    }
    public void delay(){
        try{
            sleep(100);
        }
        catch(InterruptedException e) {
            System.out.println("Exception occured");
        }
    }
    public void run(){
        while(true) {
            if (gameView.hero.lives>=0){
                reDraw();
            }else if (gameView.CurrentLevel<=0){
                reDraw();
                delay();
                reDraw();
                break;
            }

            if ((gameView.killmark==1)&&(counter<=10)){
                counter++;
            }
            else if(counter>10){
                counter=0;
                gameView.killmark=0;
            }
            delay();
        }
    }
}
