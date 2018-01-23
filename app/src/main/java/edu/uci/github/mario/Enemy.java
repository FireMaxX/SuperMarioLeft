package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public class Enemy implements GameObject {
    protected int x;
    protected int y;
    protected boolean alive;
    protected int index;
    protected boolean defend;

    public Enemy(int x, int y, int index) {
        this.x=x;
        this.y=y;
        this.index=index;
        this.alive=true;
        this.defend=false;
    }
    //Chekc if this enemy can be kill
    public boolean Killible(){return true;}
    //Check whether an item exist
    public boolean checkExist(){return alive;}
    //Mario jump onto this enemy
    public void kill(MapBlock[][] mapBlocks){
        this.alive=false;
        this.disMap(mapBlocks);
    }
    public void changeExist(boolean exist){this.alive=exist;}
    public void draw(MapBlock[][] mapBlocks){this.onMap(mapBlocks);}
    //Mario touch this enemy
    @Override
    public void visit(Mario hero, MapBlock[][] mapBlocks){
        if (this.alive){
            hero.loseLife();
        }
    }
    public void jumpon(MapBlock[][] mapBlocks){
        this.kill(mapBlocks);
        System.out.println("Kill Happen");
    }
    //Move
    public void move(int move_x, int move_y){
        this.x+=move_x;
        this.y+=move_y;
    }
    //Place an enemy on Map
    public void onMap(MapBlock[][] mapBlocks){
        mapBlocks[this.x][this.y].SetObject(this.index,this);
    }
    //Remove an enemy from Map
    public void disMap(MapBlock[][] mapBlocks){
        mapBlocks[this.x][this.y].ReSet();
    }
}
