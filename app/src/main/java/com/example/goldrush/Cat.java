package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Cat {

    boolean isGoingUp = false;
    int x, y, width, height, runCounter=0;
    Bitmap cat1, cat2, cat3, cat4, lose;
    private GameView gameview;


    Cat(GameView gameview, int screenY, Resources res){
        this.gameview = gameview;

        cat1 = BitmapFactory.decodeResource(res, R.drawable.cat1);
        cat2 = BitmapFactory.decodeResource(res, R.drawable.cat2);
        cat3 = BitmapFactory.decodeResource(res, R.drawable.cat3);
        cat4 = BitmapFactory.decodeResource(res, R.drawable.cat4);

        width = cat1.getWidth();
        height = cat1.getHeight();

        width /= 5;
        height /= 5;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        cat1 = Bitmap.createScaledBitmap(cat1, width, height,false);
        cat2 = Bitmap.createScaledBitmap(cat2, width, height,false);
        cat3 = Bitmap.createScaledBitmap(cat3, width, height,false);
        cat4 = Bitmap.createScaledBitmap(cat4, width, height,false);

        lose = BitmapFactory.decodeResource(res, R.drawable.lose);
        lose = Bitmap.createScaledBitmap(lose, width, height, false);

        y = screenY/2;
        x = (int) (64 * screenRatioX);

    }

    Bitmap getCat(){

        switch (runCounter){
            case 0:
                runCounter++;
                return cat1;
            case 2:
                runCounter++;
                return cat2;
            case 3:
                runCounter++;
                return cat3;
            default:
                runCounter = 0;
                return cat4;
        }
    }

    Rect getCollisionShape(){
        return new Rect(x, y, x+width, y+height);
    }

    Bitmap getLose(){
        return lose;
    }


}
