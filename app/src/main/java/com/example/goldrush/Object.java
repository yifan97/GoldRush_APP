package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Object {

    public int speed = 20;
    int x = 0, y, width, height;
    Bitmap bonus1, bonus2, gold, obstacle1, obstacle2, obstacle3;

    Object (Resources res){

        bonus1 = BitmapFactory.decodeResource(res, R.drawable.bonus1);
        bonus2 = BitmapFactory.decodeResource(res, R.drawable.bonus2);

        gold = BitmapFactory.decodeResource(res, R.drawable.gold);

        obstacle1 = BitmapFactory.decodeResource(res, R.drawable.obstacle1);
        obstacle2 = BitmapFactory.decodeResource(res, R.drawable.obstacle2);
        obstacle3 = BitmapFactory.decodeResource(res, R.drawable.obstacle3);

        width = gold.getWidth();
        height = gold.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height  * screenRatioY);

        bonus1 = Bitmap.createScaledBitmap(bonus1, width, height, false);
        bonus2 = Bitmap.createScaledBitmap(bonus2, width, height, false);

        gold = Bitmap.createScaledBitmap(gold, width, height, false);

        obstacle1 = Bitmap.createScaledBitmap(obstacle1, width, height, false);
        obstacle2 = Bitmap.createScaledBitmap(obstacle2, width, height, false);
        obstacle3 = Bitmap.createScaledBitmap(obstacle3, width, height, false);

        y = -height;
    }

    Rect getCollisionShape(){
        return new Rect(x, y, x+width, y+height);
    }

}
