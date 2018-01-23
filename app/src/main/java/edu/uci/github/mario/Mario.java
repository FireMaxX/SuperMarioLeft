package edu.uci.github.mario;

import java.util.Map;

/**
 * Created by fxuyi on 6/4/2017.
 */

public class Mario {
    protected  int type;   //0:Normal  1:SuperMario   2:FireMario
    protected  int lives;
    protected  int x;
    protected  int y;
    protected  int index;
    protected  boolean facing; //True:Left, False:Right
    protected  int Score;
    private int jumpFactor;
    protected int invincivle;
    //Constants
    protected final int MarioLeft=GlobleConstant.MarioLeft;
    protected final int MarioRight=GlobleConstant.MarioRight;
    protected final int SuperMarioLeft=GlobleConstant.SuperMarioLeft;
    protected final int SuperMarioRight=GlobleConstant.SuperMarioRight;
    protected final int FireMarioLeft=GlobleConstant.FireMarioLeft;
    protected final int FireMarioRight=GlobleConstant.FireMarioRight;

    public Mario(){
        this.type=0;
        this.lives=3;
        this.index=MarioLeft;
        this.facing=true;//Left
        this.x=0;
        this.y=4;
        this.Score=0;
        this.jumpFactor=1;
        this.invincivle=0;
    }
    public void getPoints(int value){
        this.Score+=value;
    }
    public void loseLife(){
        if (this.type>0){
            this.DeMode();
            this.invincivle++;
        }
        else if (this.invincivle==0){
            this.lives--;
            this.invincivle++;
        }
    }
    //Move Mario before place on Map
    public void Move(int Move_x, int Move_y, MapBlock[][] map, MapBlock[][] hero){
        int mark=this.y;
        if (Move_y<0){
            Move_y*=jumpFactor;
        }
        //Check if overflow
        int new_x=GlobleConstant.XinBoundary(x+Move_x);
        int new_y=GlobleConstant.YinBoundary(y+Move_y,type);
        //Handle Facing Direction
        if ((Move_x<0)&&facing){
            this.facing=false;
            this.index++;
        }
        else if ((Move_x>0)&&(!facing)){
            this.facing=true;
            this.index--;
        }
        //Check if Move possible
        if (Move_y==0){ //Front and Back
            if ((this.type>0)&&(map[new_x][y].passible)&&(map[new_x][y-1].passible)){
                this.x = new_x;
            }
            else if ((this.type==0)&&(map[new_x][y].passible)) {
                this.x = new_x;
            }
        }
        else if (Move_x==0){   //Up
            if (type > 0) {
                for (int i=y;i>=new_y;i--){
                    if (!map[x][i].passible) {
                        mark=i+1;
                    }
                }
                if (mark==y) {  //No block above
                    this.y = new_y;
                }
                else{
                    if (type>0){
                        this.y = mark+1;
                    }
                    else{
                        this.y = mark;
                    }
                }
            }
            else {
                if (map[new_x][new_y].passible) {
                    this.y=new_y;
                }
            }
        }
    }

    public void SuperMode(){
        this.type=1;
        this.jumpFactor=3;
        if (facing){this.index=SuperMarioLeft;}
        else {this.index=SuperMarioRight;}
    }
    public void FireMode(){
        this.type=2;
        this.jumpFactor=3;
        if (facing){this.index=FireMarioLeft;}
        else {this.index=FireMarioRight;}
    }
    public void DeMode(){
        this.type=0;
        this.jumpFactor=1;
        if (facing){this.index=MarioLeft;}
        else {this.index=MarioRight;}
    }
}
