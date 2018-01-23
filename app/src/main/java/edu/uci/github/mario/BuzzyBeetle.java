package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public class BuzzyBeetle extends Enemy {
    public BuzzyBeetle(int x, int y){
        super(x,y,GlobleConstant.BuzzyBeetleIndex);
    }
    public void DefendMode(){
        this.defend=true;
        this.index=GlobleConstant.BuzzyBeetleShellIndex;
    }
    public void onMap(MapBlock[][] mapBlocks, boolean passible){
        mapBlocks[this.x][this.y].SetObject(this.index,this,passible);
    }
    //Mario jump onto this enemy
    @Override
    public void jumpon(MapBlock[][] mapBlocks){
        if (!defend){
            this.DefendMode();
            this.onMap(mapBlocks,false);
        }
    }
    @Override
    public void move(int move_x, int move_y){
        if (!defend){   //Stop moving when hide in shell
            this.x+=move_x;
            this.y+=move_y;
        }
    }
    @Override
    public boolean Killible(){
        return !defend;
    }
}
