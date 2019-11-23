package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Gold extends Object {

    Bitmap gold;

    Gold(int type, Resources res) {
        super(type, res);
        gold = BitmapFactory.decodeResource(res, R.drawable.gold);

        width = gold.getWidth();
        height = gold.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        gold = Bitmap.createScaledBitmap(gold, width, height, false);

//        y -= height;

    }

    @Override
    Rect getCollisionShape() {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getGold(){
        return gold;
    }
}
