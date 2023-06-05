package com.samsung.cosmicsniper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class OurSpaceship {
    public int ox;
    public int oy;
    Context context;
    Bitmap ourSpaceship;
    Random random;

    public OurSpaceship(Context context) {
        this.context = context;
        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), R.drawable.rocket1);
        random = new Random();
        this.ox = random.nextInt(CosmicSniper.screenWidth);
        this.oy = CosmicSniper.screenHeight - ourSpaceship.getHeight();
    }

    public Bitmap getOurSpaceship() {
        return ourSpaceship;
    }

    int getOurSpaceshipWidth() {
        return ourSpaceship.getWidth();
    }
}
