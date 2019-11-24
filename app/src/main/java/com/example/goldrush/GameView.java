package com.example.goldrush;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Random random;
    private GameActivity activity;
    private int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private Background bg1, bg2;
    private Paint paint;
    private Object[] objects;
    private boolean isPlaying, isGameOver = false;
    private Thread thread;
    private Cat cat;
    private int score = 0;

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

        InitiateObjects();

    }

    private void InitiateObjects() {
        objects = new Object[4];
        objects[0] = new Object(0, getResources());
        objects[1] = new Object(0, getResources());

        int obstacle_picker = (int) Math.random()*3+1;
        int bonus_picker = (int) Math.random()*2 + 4;
        objects[2] = new Object(obstacle_picker, getResources());
        objects[3] = new Object(bonus_picker, getResources());
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

        bg1.x -= 30 * screenRatioX;
        bg2.x -= 30 * screenRatioX;

        if(bg1.x + bg2.background.getWidth() < 0){
            bg1.x = screenX;
        }

        if(bg2.x + bg2.background.getWidth() < 0){
            bg2.x = screenX;
        }

        if(cat.y >= screenY - cat.height){
            cat.y = screenY - cat.height;
        }

        if(cat.y <= 0){
            cat.y = 0;
        }


        for(Object object : objects){
            object.x -= object.speed;
            Log.v("TAG", "object x is: " + object.x);
            if(object.y + object.height > screenY)
                object.y = screenY-object.height;

            if(object.x + object.width < 0){

                int bound = (int) (30 * screenRatioX);
                object.speed = random.nextInt(bound);

                if(object.speed < 10 * screenRatioX){
                    object.speed = (int) (10 * screenRatioX);
                }

                object.x = screenX;
                object.y = random.nextInt(screenX-object.height);
            }


            if(Rect.intersects(cat.getCollisionShape(), object.getCollisionShape())){
                object.x -= 700;
                int type = object.getType();
                if(type == 0){
                    score += 0;
                }else if(type == 1 || type == 2 || type == 3){
                    isGameOver = true;
                    return;
                }else{
                    score += 30;
                }
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getY() < screenY / 2){
                    cat.y -= 60 * screenRatioY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(event.getY() > screenY / 2){
                    cat.y += 60 * screenRatioY;
                }
                break;
        }
        return true;
    }

    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(bg1.background, bg1.x, bg1.y, paint);
            canvas.drawBitmap(bg2.background, bg2.x, bg2.y, paint);

            for (Object object: objects){
                canvas.drawBitmap(object.getObject(), object.x, object.y, paint);
            }

//            canvas.drawText();

            if(isGameOver){
                isPlaying = false;
                canvas.drawBitmap(cat.getLose(), cat.x, cat.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveHighestScore();
                waitBeforeExiting();
                return;
            }
            canvas.drawBitmap(cat.getCat(), cat.x, cat.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }

    private void saveHighestScore() {
    }

    private void waitBeforeExiting() {

        try{
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity, Home.class));
            activity.finish();
        }catch(InterruptedException e){
            e.printStackTrace();
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
