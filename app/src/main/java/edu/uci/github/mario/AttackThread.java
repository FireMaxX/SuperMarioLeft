package edu.uci.github.mario;
import java.lang.Thread;
/**
 * Created by fxuyi on 6/6/2017.
 */

//This thread is in charge of creating and controlling fireball of FireMario
public class AttackThread extends Thread{
    GameView gameView;
    private boolean innerLocker=true;
    public AttackThread(GameView cv){
        gameView = cv;
    }

    public void run() {
        while (gameView.hero.lives > 0) {
            if (gameView.hero.type == 2) {
                if (gameView.MarioAttack && innerLocker) {
                    int x = gameView.hero.x;
                    int y = gameView.hero.y;
                    boolean facing = gameView.hero.facing;
                    gameView.fireball.newFire(x, y, facing);
                    gameView.MarioAttack = false;
                    innerLocker = false;  //Prevent a second Fireball
                }
            }
            if (gameView.fireball.exist) {
                gameView.fireball.move();
                if ((gameView.fireball.Counter > 6)||(!gameView.mapblocks[gameView.fireball.x][gameView.fireball.y].passible)) {
                    gameView.fireball.disappear(gameView.heroblocks);
                }
                else if (gameView.mapblocks[gameView.fireball.x][gameView.fireball.y].visitible!=null){
                    if (gameView.mapblocks[gameView.fireball.x][gameView.fireball.y].visitible.Killible()){
                        System.out.println("Kill");
                        gameView.killmark=1;
                        gameView.mapblocks[gameView.fireball.x][gameView.fireball.y].visitible.kill(gameView.mapblocks);
                    }
                }
            }
            else{
                innerLocker=true;   //Now new Fireball can shoot
            }
            //Delay
            try {
                sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
        System.out.println("Attack Thread Stop");
        return;
    }
}
