package com.samsung.game.CosmicSniper.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

    private final SpriteBatch batch = new SpriteBatch();

    private final Texture texture = new Texture("textures/background.png");

    private final Integer[] cells = new Integer[3];

    public Background() {
        cells[0] = 0;
        cells[1] = DefaultData.heightScreen;
        cells[2] = cells[1] * 2;
    }

    public void render(){
        cells[0] -= 5;
        cells[1] -= 5;
        cells[2] -= 5;

        for (int i = 0; i < cells.length; i++) {
            if(cells[i] < -DefaultData.heightScreen+10){
                cells[i] = DefaultData.heightScreen;
            }
        }
    }

    public void draw() {
        batch.begin();

        batch.draw(texture, 0, cells[0]);
        batch.draw(texture, 0, cells[1]);
        batch.draw(texture, 0, cells[2]);

        batch.end();
    }

    public void dispose(){
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
