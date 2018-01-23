package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public class FireFlower extends Item implements GameObject {
    public FireFlower(int x, int y){
        super(x,y,GlobleConstant.FireFlowerIndex,GlobleConstant.FireFlowerValue);
    }
    @Override
    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if (this.exist){
            hero.getPoints(this.getValue());
            hero.FireMode();
            this.exist=false;
            this.disMap(mapBlocks);
        }
    }
}
