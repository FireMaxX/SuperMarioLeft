package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/4/2017.
 */

public class MapBlock {
    public int index;
    public boolean display;
    public boolean passible;
    public GameObject visitible;

    public MapBlock(){
        this.index=0;
        this.display=false;
        this.passible=true;
        this.visitible=null;
    }
    public void SetItem(int index){ //InVisitible Items like blocks
        this.index=index;
        this.display=true;
        this.passible=false;
        this.visitible=null;
    }
    public void SetObject(int index, GameObject gameObject){   //Visitible Items like coins
        this.index=index;
        this.display=true;
        this.passible=true;
        this.visitible=gameObject;
    }
    public void SetObject(int index, GameObject gameObject, boolean passible){   //Visitible but not killible Items like Shell
        this.index=index;
        this.display=true;
        this.passible=passible;
        this.visitible=gameObject;
    }
    public void SetHero(int index,boolean display){
        this.index=index;
        this.display=display;
        this.visitible=null;
        this.passible=true;
    }
    public void ReSet(){
        this.index=0;
        this.passible=true;
        this.display=false;
        this.visitible=null;
    }
}
