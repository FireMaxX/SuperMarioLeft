package edu.uci.github.mario;
import java.lang.Thread;

/**
 * Created by fxuyi on 6/4/2017.
 */

public class MarioThread extends Thread {
    GameView gameView;
    private int delaycounter=0;
    public MarioThread(GameView cv){
        gameView = cv;
    }

    public void run(){
        while(gameView.hero.lives>0) {
            //Move
            if (delaycounter>9){
                delaycounter=0;
                switch (gameView.MarioMoveDirection){
                    case 0:{    // stay
                        break;
                    }
                    case 1:{    // Front
                        gameView.hero.Move(1,0,gameView.mapblocks,gameView.heroblocks);
                        System.out.println("Front");
                        gameView.MarioMoveDirection=0;
                        break;
                    }
                    case 2:{    // Up-Front
                        if (!gameView.JumpLocker){
                            gameView.hero.Move(0,-1,gameView.mapblocks,gameView.heroblocks);
                            gameView.hero.Move(1,0,gameView.mapblocks,gameView.heroblocks);
                            gameView.JumpLocker=true;
                        }
                        System.out.println("Up-Front");
                        gameView.MarioMoveDirection=0;
                        break;
                    }
                    case 3:{    // Up
                        if (!gameView.JumpLocker){
                            gameView.hero.Move(0,-1,gameView.mapblocks,gameView.heroblocks);
                            gameView.JumpLocker=true;
                        }
                        System.out.println("Up");
                        gameView.MarioMoveDirection=0;
                        break;
                    }
                    case 4:{    // Up-Back
                        if (!gameView.JumpLocker){
                            gameView.hero.Move(0,-1,gameView.mapblocks,gameView.heroblocks);
                            gameView.hero.Move(-1,0,gameView.mapblocks,gameView.heroblocks);
                            gameView.JumpLocker=true;
                        }
                        System.out.println("Up-Back");
                        gameView.MarioMoveDirection=0;
                        break;
                    }
                    case 5:{    // Back
                        gameView.hero.Move(-1,0,gameView.mapblocks,gameView.heroblocks);
                        System.out.println("Back");
                        gameView.MarioMoveDirection=0;
                        break;
                    }
                    default:{
                        break;
                    }
                }
                //Check if invicible mode stop
                if (gameView.hero.invincivle>6){
                    gameView.hero.invincivle=0;
                    gameView.hurtmark=0;
                }
                else if (gameView.hero.invincivle>0){
                    gameView.hero.invincivle++;
                    gameView.hurtmark=1;
                }
                System.out.println("MarioThread: Hero is now at x="+gameView.hero.x);
            }
            //Visit GameObject if possible
            if (gameView.mapblocks[gameView.hero.x][gameView.hero.y].visitible!=null){
                System.out.println("Lower Visit Happen");
                gameView.mapblocks[gameView.hero.x][gameView.hero.y].visitible.visit(gameView.hero,gameView.mapblocks);
            }
            if ((gameView.hero.type>0)&&(gameView.mapblocks[gameView.hero.x][GlobleConstant.YinBoundary(gameView.hero.y-1)].visitible!=null)){
                System.out.println("Upper Visit Happen");
                gameView.mapblocks[gameView.hero.x][gameView.hero.y-1].visitible.visit(gameView.hero,gameView.mapblocks);
            }
            //Jump and Break Box
            if ((gameView.JumpLocker)&&(gameView.hero.type>=1)&&(gameView.mapblocks[gameView.hero.x][GlobleConstant.YinBoundary(gameView.hero.y-2)].index==GlobleConstant.ClosedBoxIndex)){
                if (gameView.mapblocks[gameView.hero.x][gameView.hero.y-2].visitible!=null){
                    System.out.println("Box Break Happen");
                    gameView.mapblocks[gameView.hero.x][gameView.hero.y-2].visitible.visit(gameView.hero,gameView.mapblocks);
                }
            }
            gameView.cameraSwitch();
            delaycounter++;
            try{
                sleep(50);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
        System.out.println("Mario Thread Stop");
        return;
    }
}
