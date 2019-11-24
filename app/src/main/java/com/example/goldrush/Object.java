package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.goldrush.GameView.screenRatioX;
import static com.example.goldrush.GameView.screenRatioY;

public class Object {

    public int speed = 20;
    private int type;
    int x = 0, y, width, height;
    Resources res;

    Object (int type, Resources res){

        this.type = type;
        this.res = res;

    }

    Bitmap getObject(){
        switch (type){
            case 0:  //gold
                return getGoldBitmap();
            case 1:  //obstacle1
                return getobstacleBitmap(1);
            case 2:  //obstacle2
                return getobstacleBitmap(2);
            case 3:  //obstacle3
                return getobstacleBitmap(3);
            case 4:  //bonus1
                return getBonusBitmap(4);
            default:  //bonus2
                return getBonusBitmap(type);
        }
    }

    private Bitmap getBonusBitmap(int type) {

        Bitmap bonus1 = BitmapFactory.decodeResource(res, R.drawable.bonus1);
        Bitmap bonus2 = BitmapFactory.decodeResource(res, R.drawable.bonus2);


        width = bonus1.getWidth();
        height = bonus1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bonus1 = Bitmap.createScaledBitmap(bonus1, width, height, false);
        bonus2 = Bitmap.createScaledBitmap(bonus2, width, height, false);

        return type == 4? bonus1 : bonus2;
    }

    private Bitmap getobstacleBitmap(int type) {
        Bitmap obstacle1 = BitmapFactory.decodeResource(res, R.drawable.obstacle1);
        Bitmap obstacle2 = BitmapFactory.decodeResource(res, R.drawable.obstacle2);
        Bitmap obstacle3 = BitmapFactory.decodeResource(res, R.drawable.obstacle3);


        width = obstacle1.getWidth();
        height = obstacle1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        obstacle1 = Bitmap.createScaledBitmap(obstacle1, width, height, false);
        obstacle2 = Bitmap.createScaledBitmap(obstacle2, width, height, false);
        obstacle3 = Bitmap.createScaledBitmap(obstacle3, width, height, false);

        if (type == 1) {
            return obstacle1;
        }else if(type == 2){
            return obstacle2;
        }
        return obstacle3;
    }

    private Bitmap getGoldBitmap() {

        Bitmap gold = BitmapFactory.decodeResource(res, R.drawable.gold);

        width = gold.getWidth();
        height = gold.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        gold = Bitmap.createScaledBitmap(gold, width, height, false);

        return gold;
    }

    Rect getCollisionShape(){
        return new Rect(x, y, x + width, y + height);
    }

    int getType(){
        return this.type;
    }

}
