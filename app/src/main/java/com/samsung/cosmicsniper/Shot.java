package com.samsung.cosmicsniper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Shot {
    public int shy;
    public int shx;
    Bitmap shot;
    Context context;

    public Shot(Context context, int shx) {
        this.context = context;
        shot = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.shot);
        this.shx = shx;
    }

    public Bitmap getShot() {
        return shot;
    }

}
