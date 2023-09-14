package com.samsung.game.CosmicSniper.game;

import com.badlogic.gdx.Gdx;
import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletType;

public class TouchHandler {

    private int x = Gdx.graphics.getWidth() / 2 - DefaultData.spriteSize / 2;

    Main main;

    public TouchHandler(Main main) {
        this.main = main;
    }

    private int timer = 0;

    public void render() {

        timer++;

        if (Gdx.input.isTouched()) {

            int deltaX = (int) (Gdx.input.getX() / (Gdx.graphics.getWidth()/100f)); // место нажатия игрока в процентах от экрана

            this.x = deltaX*(DefaultData.widthScreen/100)-DefaultData.spriteSize/2; // место нажатия игрока в окне
            if (timer >= 30) {
                timer = 0;
                main.bulletManager.createBullet(BulletType.PLAYER_BULLET, main.player.getX(), main.player.getY() + DefaultData.spriteSize/2);
                SoundPlayer.playerFire.stop();
                SoundPlayer.playerFire.play();
            }
        } else {
            if (timer != 0) {
                timer = 0;
            }
        }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
