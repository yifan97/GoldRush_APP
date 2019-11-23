package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Obstacle extends Object {

    Bitmap obstacle1, obstacle2, obstacle3;

    Obstacle(int type, Resources res) {
        super(type, res);
        obstacle1 = BitmapFactory.decodeResource(res, R.drawable.obstacle1);
        obstacle2 = BitmapFactory.decodeResource(res, R.drawable.obstacle2);
        obstacle3 = BitmapFactory.decodeResource(res, R.drawable.obstacle3);


        width = obstacle1.getWidth();
        height = obstacle1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        obstacle1 = Bitmap.createScaledBitmap(obstacle1, width, height, false);
        obstacle2 = Bitmap.createScaledBitmap(obstacle2, width, height, false);
        obstacle3 = Bitmap.createScaledBitmap(obstacle3, width, height, false);


        y -= height;

    }

    Bitmap getObstacle(){
        int random = (int) (Math.random()*1.5);
        if(random < 0.5){
            return obstacle1;
        }else if(random <= 1){
            return obstacle2;
        }else{
            return obstacle3;
        }
    }

    @Override
    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}