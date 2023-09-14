package com.samsung.game.CosmicSniper.game.entity;

import com.badlogic.gdx.Gdx;
import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.DefaultData;
import com.samsung.game.CosmicSniper.game.TouchHandler;

public class Player extends Entity {

    public int hp = 10;
    public int score = 0;
    public int record = 0;

    public final TouchHandler touchHandler;

    public int numTexture = 0;

    public Player(int x, int y, Main main) {
        super(x, y);
        touchHandler = new TouchHandler(main);
    }

    private int timer = 0;

    public void render() {
        super.render();
        touchHandler.render();
        if (Gdx.input.isTouched()) {
            if (this.x > touchHandler.getX() + 10 && this.x > 0) {
                this.x -= 5;
            } else if (this.x < touchHandler.getX() - 10 && this.x + DefaultData.spriteSize < DefaultData.widthScreen) {
                this.x += 5;
            }
        }

        timer++;
        if (timer >= 20) {
            timer = 0;
            if (numTexture == 1) {
                numTexture = 0;
            } else {
                numTexture = 1;
            }
        }

    }

}
