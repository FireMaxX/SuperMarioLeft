package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/8/2017.
 */

public class Box extends Item implements GameObject {
    protected boolean open;
    protected Item item;
    public Box(int x, int y){
        super(x,y,GlobleConstant.ClosedBoxIndex,0);
        this.open=false;
        this.item=null;
    }
    @Override
    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if ((this.exist)&&(!this.open)){
            this.open=true;
            if (this.item!=null){
                this.item.changeExist(true);
                this.item.onMap(mapBlocks);
            }
            this.index=GlobleConstant.OpenedBoxIndex;
            this.onMap(mapBlocks);
        }
    }
    //Place an item on Map
    public void onMap(MapBlock[][] mapBlocks){
        mapBlocks[this.x][this.y].SetObject(this.index,this,false);
    }
}
