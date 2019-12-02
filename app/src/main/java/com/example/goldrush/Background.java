package com.example.goldrush;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {

    public Bitmap background;

    public int x = 0, y = 0;

    public Background(int screenX, int screenY, Resources res){
        background = BitmapFactory.decodeResource(res, R.drawable.gamebackground);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);
    }
}
