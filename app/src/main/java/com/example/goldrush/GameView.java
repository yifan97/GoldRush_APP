package com.example.goldrush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Random random;
    private GameActivity activity;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Background bg1, bg2;
    private Paint paint;
    private Object objects[];
    private boolean isPlaying, isGameOver = false;
    private Thread thread;
    private Cat cat;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //sound pool
        }else{
            //another sound pool
        }

        this.screenX = screenX;
        this.screenY = screenY;

        screenRatioX = 1920f/screenX;
        screenRatioY = 1080f/screenY;

        bg1 = new Background(screenX, screenY, getResources());
        bg2 = new Background(screenX, screenY, getResources());

        cat = new Cat(this, screenY, getResources());

        bg1.x = screenX;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);

        objects = new Object[5];

        random = new Random();



    }

    @Override
    public void run() {
        
        while(isPlaying){
            
            update();
            draw();
            sleep();
            
        }

    }

    private void update() {

        bg1.x -= 10 * screenRatioX;
        bg2.x -= 10 * screenRatioX;

        if(bg1.x + bg2.background.getWidth() < 0){
            bg1.x = screenX;
        }

        if(bg2.x + bg2.background.getWidth() < 0){
            bg2.x = screenX;
        }

        if(cat.isGoingUp){
            cat.y -= 30 * screenRatioY;
        }else{
            cat.y += 30 * screenRatioY;
        }

        if(cat.y >= screenY - cat.height){
            cat.y = 0;
        }

        if(cat.y >= screenY - cat.height){
            cat.y = screenY - cat.height;
        }



    }

    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bg1.background, bg1.x, bg1.y, paint);
            canvas.drawBitmap(bg2.background, bg2.x, bg2.y, paint);

//            canvas.drawText();

            if(isGameOver){
                isPlaying = false;
                canvas.drawBitmap(cat.getLose(), cat.x, cat.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }

            canvas.drawBitmap(cat.getCat(), cat.x, cat.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }


    private void sleep(){

        try{
            Thread.sleep(17);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }


    public void pause() {

        try {
            isPlaying = false;
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    public void resume() {

        isPlaying = true;
        thread = new Thread(this);
        thread.start();

    }
}
