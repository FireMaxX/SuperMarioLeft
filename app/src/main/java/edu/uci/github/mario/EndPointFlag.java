package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/8/2017.
 */

//Touch this flag will transfer Mario to next level
public class EndPointFlag extends Item implements GameObject {
    public EndPointFlag(int x, int y){
        super(x,y,23,0);
    }

    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if (this.exist){
            //New Level
            hero.lives-=9;
            hero.x=0;
            hero.y=4;
            hero.invincivle=0;
            System.out.println("Hero's lives="+hero.lives);
        }
    }

}
