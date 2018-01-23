package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public interface GameObject {
    //Visit a GameObject, can be enemy or item
    public void visit(Mario hero, MapBlock[][] mapBlocks);
    //Kill a enemy
    public void kill(MapBlock[][] mapBlocks);
    //Jump onto a item
    public void jumpon(MapBlock[][] mapBlocks);
    //Check if item exist
    public boolean checkExist();
    //Return judgement if Object can be kill
    public boolean Killible();
    public void changeExist(boolean exist);
    public void draw(MapBlock[][] mapBlocks);
}
