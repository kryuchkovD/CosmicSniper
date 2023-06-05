package com.samsung.cosmicsniper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class HostileSpaceship {
    Context context;
    Bitmap hostileSpaceship;
    static int ex = 200;
    static int ey = 0;
    int hostileVelocity;
    Random random;

    public HostileSpaceship(Context context) {
        this.context = context;
        hostileSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket2);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        hostileVelocity = 14 + random.nextInt(10);
    }

    public Bitmap getHostileSpaceship() {
        return hostileSpaceship;
    }

    int getHostileSpaceshipWidth() {
        return hostileSpaceship.getWidth();
    }

}
