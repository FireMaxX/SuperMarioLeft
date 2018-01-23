package edu.uci.github.mario;

/**
 * Created by fxuyi on 6/5/2017.
 */

public class Coin extends Item implements GameObject {
    public Coin(int x, int y){
        super(x,y,GlobleConstant.CoinIndex,GlobleConstant.CoinValue);
    }
}
