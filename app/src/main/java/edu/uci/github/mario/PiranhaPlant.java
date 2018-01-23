package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/7/2017.
 */

public class PiranhaPlant extends Enemy {
    protected boolean hide;
    public PiranhaPlant(int x, int y){
        super(x,y,GlobleConstant.PiranhaplantIndex);
    }

    //Move
    public void move(int move_x, int move_y){
        if (move_y==1){  //Hide
            this.hide=true;
        }
        if (move_y==-1){   //Reshow
            this.hide=false;
        }
    }
}
