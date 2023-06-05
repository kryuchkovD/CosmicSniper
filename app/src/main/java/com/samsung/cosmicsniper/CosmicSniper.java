package com.samsung.cosmicsniper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class CosmicSniper extends View {
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    private static final int UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 0;
    int life = 3;
    Paint scorePaint;
    private static final int TEXT_SIZE = 80;
    boolean paused = false;
    OurSpaceship ourSpaceship;
    HostileSpaceship hostileSpaceship;
    Random random;
    ArrayList<Shot> hostileShots, ourShots;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    boolean hostileShotAction = false;
    final Runnable runnable = this::invalidate;


    public CosmicSniper(Context context) {
        super(context);
        this.context = context;
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        hostileShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        explosions = new ArrayList<>();
        ourSpaceship = new OurSpaceship(context);
        hostileSpaceship = new HostileSpaceship(context);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("Pt: " + points, 0, TEXT_SIZE, scorePaint);
        for (int i = life; i >= 1; i--) {
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }

        if (life == 0) {
            paused = true;
            handler = null;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        HostileSpaceship.ex += hostileSpaceship.hostileVelocity;

        if (HostileSpaceship.ex + hostileSpaceship.getHostileSpaceshipWidth() >= screenWidth) {
            hostileSpaceship.hostileVelocity *= -1;
        }

        if (HostileSpaceship.ex <= 0) {
            hostileSpaceship.hostileVelocity *= -1;
        }

        if (!hostileShotAction) {
            if (HostileSpaceship.ex >= 200 + random.nextInt(400)) {
                Shot hostileShot = new Shot(context, HostileSpaceship.ex + hostileSpaceship.getHostileSpaceshipWidth() / 2);
                hostileShots.add(hostileShot);

                hostileShotAction = true;
            }
            Shot hostileShot = new Shot(context, HostileSpaceship.ex + hostileSpaceship.getHostileSpaceshipWidth() / 2);
            hostileShots.add(hostileShot);
            hostileShotAction = true;
        }

        canvas.drawBitmap(hostileSpaceship.getHostileSpaceship(), HostileSpaceship.ex, HostileSpaceship.ey, null);

        if (ourSpaceship.ox > screenWidth - ourSpaceship.getOurSpaceshipWidth()) {
            ourSpaceship.ox = screenWidth - ourSpaceship.getOurSpaceshipWidth();
        } else if (ourSpaceship.ox < 0) {
            ourSpaceship.ox = 0;
        }

        canvas.drawBitmap(ourSpaceship.getOurSpaceship(), ourSpaceship.ox, ourSpaceship.oy, null);

        for (int i = 0; i < hostileShots.size(); i++) {
            hostileShots.get(i).shy += 15;
            canvas.drawBitmap(hostileShots.get(i).getShot(), hostileShots.get(i).shx, hostileShots.get(i).shy, null);
            if ((hostileShots.get(i).shx >= ourSpaceship.ox)
                    && hostileShots.get(i).shx <= ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth()
                    && hostileShots.get(i).shy >= ourSpaceship.oy
                    && hostileShots.get(i).shy <= screenHeight) {
                life--;
                hostileShots.remove(i);
                explosion = new Explosion(context);
                explosions.add(explosion);
            } else if (hostileShots.get(i).shy >= screenHeight) {
                hostileShots.remove(i);
            }
            if (hostileShots.size() < 1) {
                hostileShotAction = false;
            }
        }

        for (int i = 0; i < ourShots.size(); i++) {
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
            if ((ourShots.get(i).shx >= HostileSpaceship.ex)
                    && ourShots.get(i).shx <= HostileSpaceship.ex + hostileSpaceship.getHostileSpaceshipWidth()
                    && ourShots.get(i).shy <= hostileSpaceship.getHostileSpaceshipWidth()
                    && ourShots.get(i).shy >= HostileSpaceship.ey) {
                points++;
                ourShots.remove(i);
                explosion = new Explosion(context);
                explosions.add(explosion);
            } else if (ourShots.get(i).shy <= 0) {
                ourShots.remove(i);
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).eX, explosions.get(i).eY, null);
            explosions.get(i).explosionFrame++;
            if (explosions.get(i).explosionFrame > 8) {
                explosions.remove(i);
            }
        }

        if (!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();


        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (ourShots.size() < 1) {
                Shot ourShot = new Shot(context, ourSpaceship.ox + ourSpaceship.getOurSpaceshipWidth() / 2);
                ourShots.add(ourShot);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ourSpaceship.ox = touchX;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ourSpaceship.ox = touchX;
        }

        return true;
    }
}
