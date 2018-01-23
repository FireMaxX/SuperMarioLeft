package edu.uci.github.mario;

import android.view.MotionEvent;

import java.util.Map;
import java.util.Properties;

/**
 * Created by fxuyi on 6/7/2017.
 */

public class GlobleConstant {
    protected static final int TubeIndex=1;
    protected static final int BlockIndex=2;
    protected static final int BlooberIndex=3;
    protected static final int BuzzyBeetleIndex=4;
    protected static final int BuzzyBeetleShellIndex=5;
    protected static final int PiranhaplantIndex=6;
    protected static final int MushroomIndex=7;
    protected static final int MushroomValue=1000;
    protected static final int FireBallIndex=8;
    protected static final int FireFlowerIndex=9;
    protected static final int FireFlowerValue=1000;
    protected static final int MarioLeft=10;
    protected static final int MarioRight=11;
    protected static final int SuperMarioLeft=12;
    protected static final int SuperMarioRight=13;
    protected static final int FireMarioLeft=14;
    protected static final int FireMarioRight=15;
    protected static final int CoinIndex=21;
    protected static final int CoinValue=200;
    protected static final int ClosedBoxIndex=24;
    protected static final int OpenedBoxIndex=25;

    protected static final int AmountofColumn=15;
    protected static final int AmountofRow=8;
    protected static final int MapLength=75;
    protected static final int MapHeight=6;

    //Ensure x will not beyong de map boundary
    public static int XinBoundary(int x){
        int ret=x;
        if (x<0){
            ret=0;
        }
        else if (x> MapLength){
            ret = MapLength;
        }
        return ret;
    }
    //Ensure y will not beyong de map boundary
    public static int YinBoundary(int y){
        int ret=y;
        if (y<0){
            ret=0;
        }
        else if (y> MapHeight){
            ret = MapHeight;
        }
        return ret;
    }
    public static int YinBoundary(int y, int type){
        int ret=y;
        if (type>0){
            if (y-1<0){
                ret=1;
            }
        }
        else {
            if (y<0){
                ret=0;
            }
        }
        if (y> MapHeight){
            ret = MapHeight;
        }
        return ret;
    }
}
