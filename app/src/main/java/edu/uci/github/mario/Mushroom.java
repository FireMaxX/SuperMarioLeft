package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public class Mushroom extends Item implements GameObject {
    public Mushroom(int x, int y){
        super(x,y,GlobleConstant.MushroomIndex,GlobleConstant.MushroomValue);
    }

    @Override
    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if (this.exist){
            hero.getPoints(this.getValue());
            if (hero.type==0){
                hero.SuperMode();
            }
            this.exist=false;
            this.disMap(mapBlocks);
        }
    }
}
