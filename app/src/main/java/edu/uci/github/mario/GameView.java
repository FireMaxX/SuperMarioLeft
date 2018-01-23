package edu.uci.github.mario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by fxuyi on 6/3/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    //Constants
    protected final int BlockIndex=GlobleConstant.BlockIndex;
    protected final int TubeIndex=GlobleConstant.TubeIndex;
    protected final int SuperMarioLeft=GlobleConstant.SuperMarioLeft;
    protected final int SuperMarioRight=GlobleConstant.SuperMarioRight;
    protected final int FireMarioLeft=GlobleConstant.FireMarioLeft;
    protected final int FireMarioRight=GlobleConstant.FireMarioRight;
    protected final int AmountofColumn=GlobleConstant.AmountofColumn;
    protected final int AmountofRow=GlobleConstant.AmountofRow;
    protected final int MapLength=GlobleConstant.MapLength;
    protected final int MapHeight=GlobleConstant.MapHeight;
    //Objects
    protected Bitmap blockview[];
    protected MapBlock mapblocks[][];
    protected MapBlock camera[][];
    protected MapBlock heroblocks[][];
    protected Mario hero=new Mario();
    protected FireBall fireball=new FireBall();
    protected Coin coin[];
    protected Coin hide_coin[];
    protected Mushroom mushroom[];
    protected Mushroom hide_mushroom[];
    protected FireFlower fireFlower[];
    protected FireFlower hide_fireFlower[];
    protected Box box[];
    protected BuzzyBeetle buzzyBeetle[];
    protected Bloober bloober[];
    protected PiranhaPlant piranhaPlant[];
    protected EndPointFlag endPointFlag=new EndPointFlag(MapLength-10,4);
    //Variables
    protected int MarioMoveDirection=0;
    protected boolean MarioAttack=false;
    protected boolean JumpLocker=false;
    protected int CurrentLevel=1;
    protected int LevelInfoCounter=15;
    protected int killmark=0;
    protected int hurtmark=0;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        blockview = new Bitmap[26];
        startNewlevel(1);   //Game Start
    }

    public void startNewlevel(int level){
        mapblocks = new MapBlock[MapLength][MapHeight];
        heroblocks = new MapBlock[AmountofColumn][AmountofRow];
        camera = new MapBlock[AmountofColumn][AmountofRow];
        hero.x=0;
        hero.y=4;
        //Initialize Map Blocks
        for (int i=0;i<MapLength;i++){
            for (int j=0;j<MapHeight;j++) {
                mapblocks[i][j]=new MapBlock();
            }
        }
        for (int i=0;i<MapLength;i++){
            mapblocks[i][5].SetItem(BlockIndex); //Ground
        }
        //Initialize Camera&Hero View
        for (int i=0;i<AmountofColumn;i++){
            for (int j=0;j<AmountofRow;j++) {
                camera[i][j]=new MapBlock();    //Empty array
                heroblocks[i][j]=new MapBlock();
            }
        }
        this.cameraSwitch();
        //Set Fixed Control Button
        camera[12][6].SetItem(16);
        camera[13][6].SetItem(17);
        camera[14][6].SetItem(18);
        camera[12][7].SetItem(19);
        camera[14][7].SetItem(20);
        // Read Map
        switch (level){
            default:{
                readFromfile("mapLevel1");
                break;
            }
            case 2:{
                readFromfile("mapLevel2");
                break;
            }
            case 3:{
                readFromfile("mapLevel3");
                break;
            }
        }
        mapblocks[MapLength-10][4].SetObject(23,endPointFlag);
        for (int j=0;j<GlobleConstant.MapHeight;j++){
            mapblocks[MapLength-9][j].SetItem(BlockIndex); //Block Wall
        }
        LevelInfoCounter=25;
        //Thread Restart
        MarioThread marioThread=new MarioThread(this);
        GravityThread gravityThread=new GravityThread(this);
        AttackThread attackThread=new AttackThread(this);
        BuzzyBeetleThread buzzyBeetleThread=new BuzzyBeetleThread(this);
        BlooberThread blooberThread=new BlooberThread(this);
        ItemRedrawThread itemRedrawThread=new ItemRedrawThread(this);
        PiranhaPlantThread piranhaPlantThread=new PiranhaPlantThread(this);
        marioThread.start();
        gravityThread.start();
        attackThread.start();
        buzzyBeetleThread.start();
        blooberThread.start();
        itemRedrawThread.start();
        piranhaPlantThread.start();
    }

    public void cameraSwitch(){
        int hero_x=hero.x;
        int CameraStartX=0;
        //Make sure hero appears at the middle of the Screen
        if (hero_x>7){CameraStartX=hero_x-7;}
        else {CameraStartX=0;}
        //Copy Display Info
        for (int i=0;i<AmountofColumn;i++){
            for (int j=0;j<AmountofRow-2;j++) {
                camera[i][j].index=mapblocks[i+CameraStartX][j].index;
                camera[i][j].display=mapblocks[i+CameraStartX][j].display;
            }
        }
        //Copy Hero&Fireball Display;
        int fireball_x=fireball.x-hero_x;
        if ((fireball_x>7)||(fireball_x<-7)){fireball.disappear(heroblocks);}    //Handle overflow
        int hero_y=hero.y;
        for (int i=0;i<AmountofColumn;i++){
            for (int j=0;j<AmountofRow-2;j++) {
                heroblocks[i][j].ReSet();   //Clean
            }
        }
        if (hero.type>0){
            hero_y-=1;
        }
        if (hero_x>7){
            heroblocks[7][hero_y].index=hero.index;
            heroblocks[7][hero_y].display=true;
            if (fireball.exist){
                heroblocks[7+fireball_x][fireball.y].index=fireball.index;
                heroblocks[7+fireball_x][fireball.y].display=true;
            }
        }
        else {
            heroblocks[hero_x][hero_y].index=hero.index;
            heroblocks[hero_x][hero_y].display=true;
            if (fireball.exist){
                heroblocks[fireball.x][fireball.y].index=fireball.index;
                heroblocks[fireball.x][fireball.y].display=true;
            }
        }
    }
    //Get Whole Map From Level-File
    public void readFromfile(String FileName){
        try {
            InputStreamReader Read = new InputStreamReader(getResources().getAssets().open(FileName));
            BufferedReader BufferReader = new BufferedReader(Read);
            String Line = BufferReader.readLine();  //Read the first line
            while(Line != null){
                System.out.println(Line);
                analysisMapcommand((Line));
                Line = BufferReader.readLine();
            }
            Read.close();
            return;
        } catch (Exception e) {
            System.out.println("I/O Error");
        }
    }
    //Translate and Load Map File Info
    public void analysisMapcommand(String input) {
        if (input.indexOf("Tube")!=-1){
            String Sp[]=input.split(",");
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                mapblocks[x][y].SetItem(TubeIndex);
                mapblocks[x+1][y].SetItem(TubeIndex);
                mapblocks[x+1][y].display=false;
                mapblocks[x][y+1].SetItem(TubeIndex);
                mapblocks[x][y+1].display=false;
                mapblocks[x+1][y+1].SetItem(TubeIndex);
                mapblocks[x+1][y+1].display=false;
            }
        }
        if (input.indexOf("Box")!=-1){
            String Sp[]=input.split(",");
            box =new Box[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                box[counter]=new Box(x,y);
                box[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("Coin")!=-1){
            String Sp[]=input.split(",");
            coin =new Coin[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                coin[counter]=new Coin(x,y);
                coin[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("Cib")!=-1){
            String Sp[]=input.split(",");
            hide_coin =new Coin[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                hide_coin[counter]=new Coin(x,y);
                for (Box b:box){
                    if ((b.x==x)&&(b.y==y+1)){
                        System.out.println("One Coin in box");
                        hide_coin[counter].exist=false;
                        b.item=hide_coin[counter];
                    }
                }
                counter++;
            }
        }
        if (input.indexOf("Mushroom")!=-1){
            String Sp[]=input.split(",");
            mushroom =new Mushroom[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                mushroom[counter]=new Mushroom(x,y);
                mushroom[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("Mib")!=-1){
            String Sp[]=input.split(",");
            hide_mushroom =new Mushroom[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                hide_mushroom[counter]=new Mushroom(x,y);
                for (Box b:box){
                    if ((b.x==x)&&(b.y==y+1)){
                        System.out.println("One Mushroom in box");
                        hide_mushroom[counter].exist=false;
                        b.item=hide_mushroom[counter];
                    }
                }
                counter++;
            }
        }
        if (input.indexOf("FireFlower")!=-1){
            String Sp[]=input.split(",");
            fireFlower =new FireFlower[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                fireFlower[counter]=new FireFlower(x,y);
                fireFlower[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("Fib")!=-1){
            String Sp[]=input.split(",");
            hide_fireFlower =new FireFlower[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                hide_fireFlower[counter]=new FireFlower(x,y);
                for (Box b:box){
                    if ((b.x==x)&&(b.y==y+1)){
                        System.out.println("One Flower in box");
                        hide_fireFlower[counter].exist=false;
                        b.item=hide_fireFlower[counter];
                    }
                }
                counter++;
            }
        }
        if (input.indexOf("BuzzyBeetle")!=-1){
            String Sp[]=input.split(",");
            buzzyBeetle =new BuzzyBeetle[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                buzzyBeetle[counter]=new BuzzyBeetle(x,y);
                buzzyBeetle[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("Bloober")!=-1){
            String Sp[]=input.split(",");
            bloober =new Bloober[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                bloober[counter]=new Bloober(x,y);
                bloober[counter].onMap(mapblocks);
                counter++;
            }
        }
        if (input.indexOf("PiranhaPlant")!=-1){
            String Sp[]=input.split(",");
            piranhaPlant =new PiranhaPlant[(Sp.length-1)/2];
            int counter=0;
            for (int i=1;i<Sp.length;i+=2){
                int x=Integer.parseInt(Sp[i]);
                int y=Integer.parseInt(Sp[i+1]);
                piranhaPlant[counter]=new PiranhaPlant(x,y);
                piranhaPlant[counter].onMap(mapblocks);
                counter++;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int TouchX;
        int TouchY;
        int IndexX;
        int IndexY;
        int width = getWidth();
        int height = getHeight();
        int rowHeight = height / AmountofRow;
        int columnWidth = width / AmountofColumn;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {  //Code from TA, Thanks
            System.out.println("ACTION_DOWN");
            TouchX = (int) event.getX();
            TouchY = (int) event.getY();
            IndexX = TouchX / columnWidth;
            IndexY = TouchY / rowHeight;
            //Get Move Direction
            if ((IndexX==12)&&(IndexY==6)){
                MarioMoveDirection=2;   //Up-Front
            }
            else if ((IndexX==13)&&(IndexY==6)){
                MarioMoveDirection=3;   //Up
            }
            else if ((IndexX==14)&&(IndexY==6)){
                MarioMoveDirection=4;   //Up-Back
            }
            else if ((IndexX==12)&&(IndexY==7)){
                MarioMoveDirection=1;   //Front
            }
            else if ((IndexX==14)&&(IndexY==7)){
                MarioMoveDirection=5;   //Back
            }
            else {
                MarioAttack=true;   //Attack
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("ACTION_UP");
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DrawThread drawThread=new DrawThread(this);
        LevelControlThread levelControlThread=new LevelControlThread(this);
        //LandScape
        blockview[0] = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        blockview[1] = BitmapFactory.decodeResource(getResources(), R.drawable.tube);
        blockview[2] = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        blockview[3] = BitmapFactory.decodeResource(getResources(), R.drawable.bloober);
        blockview[4] = BitmapFactory.decodeResource(getResources(), R.drawable.buzzybeetle);
        blockview[5] = BitmapFactory.decodeResource(getResources(), R.drawable.buzzybeetleshell);
        blockview[6] = BitmapFactory.decodeResource(getResources(), R.drawable.piranhaplant);
        blockview[7] = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);
        blockview[8] = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
        blockview[9] = BitmapFactory.decodeResource(getResources(), R.drawable.fireflower);
        blockview[10] = BitmapFactory.decodeResource(getResources(), R.drawable.marioleft);
        blockview[11] = BitmapFactory.decodeResource(getResources(), R.drawable.marioright);
        blockview[12] = BitmapFactory.decodeResource(getResources(), R.drawable.supermarioleft);
        blockview[13] = BitmapFactory.decodeResource(getResources(), R.drawable.supermarioright);
        blockview[14] = BitmapFactory.decodeResource(getResources(), R.drawable.firemarioleft);
        blockview[15] = BitmapFactory.decodeResource(getResources(), R.drawable.firemarioright);
        blockview[16] = BitmapFactory.decodeResource(getResources(), R.drawable.upleft);
        blockview[17] = BitmapFactory.decodeResource(getResources(), R.drawable.up);
        blockview[18] = BitmapFactory.decodeResource(getResources(), R.drawable.upright);
        blockview[19] = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        blockview[20] = BitmapFactory.decodeResource(getResources(), R.drawable.right);
        blockview[21] = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
        blockview[22] = BitmapFactory.decodeResource(getResources(), R.drawable.attack);
        blockview[23] = BitmapFactory.decodeResource(getResources(), R.drawable.endpoint);
        blockview[24] = BitmapFactory.decodeResource(getResources(), R.drawable.boxclose);
        blockview[25] = BitmapFactory.decodeResource(getResources(), R.drawable.boxopen);
        drawThread.start();
        levelControlThread.start();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        Rect rect = new Rect();
        int width = getWidth();
        int height = getHeight();
        int rowHeight = height / AmountofRow;
        int columnWidth = width / AmountofColumn;
        int index=0;
        //Display Lower Layer (Map)
        for (int i=0;i<AmountofColumn;i++){
            for (int j=0;j<AmountofRow-2;j++) {
                if (camera[i][j].display){
                    index=camera[i][j].index;
                    if ((index==TubeIndex)){    //Draw MapObjects
                        rect.set((AmountofColumn-i-2) * columnWidth, j * rowHeight, (AmountofColumn-i) * columnWidth, (j + 2) * rowHeight);
                    }
                    else{
                        rect.set((AmountofColumn-i-1) * columnWidth, j * rowHeight, (AmountofColumn-i) * columnWidth, (j + 1) * rowHeight);
                    }
                    canvas.drawBitmap(blockview[camera[i][j].index], null, rect, null);
                }

            }
        }//Display Upper Layer (Hero)
        for (int i=0;i<AmountofColumn;i++){
            for (int j=0;j<AmountofRow-2;j++) {
                if (heroblocks[i][j].display){  //Draw Hero and Fireball
                    index=heroblocks[i][j].index;
                    if ((index==SuperMarioLeft)||(index==SuperMarioRight)||(index==FireMarioLeft)||(index==FireMarioRight)){
                        rect.set((AmountofColumn-i-1) * columnWidth, j * rowHeight, (AmountofColumn-i) * columnWidth, (j + 2) * rowHeight);
                    }
                    else{
                        rect.set((AmountofColumn-i-1) * columnWidth, j * rowHeight, (AmountofColumn-i) * columnWidth, (j + 1) * rowHeight);
                    }
                    canvas.drawBitmap(blockview[heroblocks[i][j].index], null, rect, null);
                }
            }
        }
        //Display Button
        for (int i=0;i<AmountofColumn;i++){
            for (int j=AmountofRow-2;j<AmountofRow;j++) {
                rect.set(i * columnWidth, j * rowHeight, (i+1) * columnWidth, (j + 1) * rowHeight);
                canvas.drawBitmap(blockview[camera[i][j].index], null, rect, null);
            }
        }
        rect.set(0 * columnWidth, 6 * rowHeight, 2 * columnWidth, 8 * rowHeight);
        canvas.drawBitmap(blockview[22], null, rect, null);
        //Display Score
        Paint p = new Paint(Color.RED);
        p.setTextSize(100);  // Set text size
        p.setColor(Color.RED);
        p.setTextAlign(Paint.Align.CENTER);
        p.setFakeBoldText(true);
        Integer score=hero.Score;
        Integer lives=hero.lives;
        Integer level=CurrentLevel;
        String  CurrentLevelDisplay="Level-"+level.toString();
        canvas.drawText(score.toString(), 12*width/13,height/10,p);
        canvas.drawText(lives.toString(), 60,height/10,p);
        if (LevelInfoCounter>0){
            canvas.drawText(CurrentLevelDisplay,width/2,height/10,p);
            LevelInfoCounter--;
        }
        else if (level==0){
            canvas.drawText("GameOver!",width/2,height/10,p);
        }
        else if (level==-1){
            p.setColor(Color.GREEN);
            canvas.drawText("Winner!",width/2,height/10,p);
        }
        //Display motion marks
        if (killmark==1){
            Paint m = new Paint(Color.BLACK);
            m.setTextSize(80);  // Set text size
            m.setColor(Color.BLUE);
            m.setTextAlign(Paint.Align.CENTER);
            p.setFakeBoldText(true);
            String Message="Nice Kill!";
            canvas.drawText(Message, width/2,height/10,m);
        }
        else if (hurtmark==1){
            Paint m = new Paint(Color.BLACK);
            m.setTextSize(80);  // Set text size
            m.setColor(Color.RED);
            m.setTextAlign(Paint.Align.CENTER);
            p.setFakeBoldText(true);
            String Message="Hurt! Invincible for Now";
            canvas.drawText(Message,width/2,height/10,m);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

    }

}
