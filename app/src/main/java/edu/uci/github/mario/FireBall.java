package edu.uci.github.mario;

import java.util.Map;

/**
 * Created by fxuyi on 6/6/2017.
 */

public class FireBall {
    protected int x;
    protected int y;
    protected int index;
    protected boolean exist;
    protected int Counter;
    private boolean direction; //true->Left

    public FireBall(){
        this.x=0;
        this.y=0;
        this.index=GlobleConstant.FireBallIndex;
        this.exist=false;
        this.direction=true;
        this.Counter=0;
    }
    public void newFire(int x, int y, boolean facing){
        this.x=x;
        this.y=y;
        this.exist=true;
        this.direction=facing;
        this.Counter=0;
    }
    public void move(){
        if (direction){
            this.x+=1;
        }
        else{
            this.x-=1;
        }
        //Handle overflow
        if (this.x>GlobleConstant.MapLength){
            this.exist=false;
            this.x=GlobleConstant.MapLength;
        }
        else if (this.x<0){
            this.exist=false;
            this.x=0;
        }
        else{
            this.Counter++;
        }
    }
    //Fire disappear under certain conditions
    public void disappear(MapBlock[][] mapBlocks){
        this.Counter=0;
        this.exist=false;
    }
    public void killenemy(MapBlock[][] mapBlocks){
        GameObject enemy=mapBlocks[this.x][this.y].visitible;
        if (enemy.Killible()){
            enemy.kill(mapBlocks);
        }
    }
}
