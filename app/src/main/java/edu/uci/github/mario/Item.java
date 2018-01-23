package edu.uci.github.mario;


/**
 * Created by fxuyi on 5/24/2017.
 */

public class Item implements GameObject{
    private int value;
    protected int x;
    protected int y;
    protected boolean exist;
    protected int index;

    public Item(int x, int y, int index, int value) {
        this.x=x;
        this.y=y;
        this.index=index;
        this.value=value;
        this.exist=true;
    }
    //Item cannot be killed or jumpon
    public boolean Killible(){return false;}
    public void kill(MapBlock[][] mapBlocks){}
    public void jumpon(MapBlock[][] mapBlocks){}
    //Check whether an item exist
    public boolean checkExist(){return exist;}
    public void changeExist(boolean exist){this.exist=exist;}
    public void draw(MapBlock[][] mapBlocks){this.onMap(mapBlocks);}
    //Return Item Reward value
    public int getValue(){
        return value;
    }
    // Mario vist this item if passible
    @Override
    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if (this.exist){
            hero.getPoints(this.getValue());
            this.exist=false;
            this.disMap(mapBlocks);
        }
    }
    //Place an item on Map
    public void onMap(MapBlock[][] mapBlocks){
        mapBlocks[this.x][this.y].SetObject(this.index,this);
    }
    //Remove an item from Map
    public void disMap(MapBlock[][] mapBlocks){
        mapBlocks[this.x][this.y].ReSet();
    }
}
