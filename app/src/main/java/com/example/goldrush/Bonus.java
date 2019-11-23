package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Bonus extends Object {

    Bitmap bonus1, bonus2;

    Bonus(int type, Resources res) {
        super(type, res);
        bonus1 = BitmapFactory.decodeResource(res, R.drawable.bonus1);
        bonus2 = BitmapFactory.decodeResource(res, R.drawable.bonus2);


        width = bonus1.getWidth();
        height = bonus1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bonus1 = Bitmap.createScaledBitmap(bonus1, width, height, false);
        bonus2 = Bitmap.createScaledBitmap(bonus2, width, height, false);

        y -= height;

    }

    Bitmap getBonus(){
        int random = (int) Math.random();
        if(random < 0.5){
            return bonus1;
        }else{
            return bonus2;
        }
    }

    @Override
    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }
}