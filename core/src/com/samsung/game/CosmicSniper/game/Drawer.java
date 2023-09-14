package com.samsung.game.CosmicSniper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.entity.enemy.Enemy;
import com.samsung.game.CosmicSniper.game.entity.enemy.EnemyType;
import com.samsung.game.CosmicSniper.game.entity.shooting.Bullet;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletType;

public class Drawer {

    SpriteBatch batch;

    Main main;

    private final Texture[] textures = new Texture[7];
    private final Texture[] enemyType1 = new Texture[2];
    private final Texture[] enemyType2 = new Texture[2];
    private final Texture[] boss = new Texture[2];

    BitmapFont font;

    public Drawer(Main main) {
        batch = new SpriteBatch();
        this.main = main;

        textures[0] = new Texture("textures/player/1.png");
        textures[1] = new Texture("textures/player/2.png");
        textures[2] = new Texture("textures/player/bullet.png");
        textures[3] = new Texture("textures/enemy/bullet.png");
        textures[4] = new Texture("textures/restart.png");
        textures[5] = new Texture("textures/particles/1.png");
        textures[6] = new Texture("textures/win.png");
        enemyType1[0] = new Texture("textures/enemy/type1/1.png");
        enemyType1[1] = new Texture("textures/enemy/type1/2.png");
        enemyType2[0] = new Texture("textures/enemy/type2/1.png");
        enemyType2[1] = new Texture("textures/enemy/type2/2.png");
        boss[0] = new Texture("textures/enemy/boss/1.png");
        boss[1] = new Texture("textures/enemy/boss/2.png");

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (DefaultData.spriteSize / 2.5f);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));

        parameter.color = new Color(1f, 1f, 1f, 1);
        font = generator.generateFont(parameter);
    }

    public void render() {
        batch.begin();

        for (Bullet bullet : main.bulletManager.bullets) {
            if (bullet.getBulletType() == BulletType.PLAYER_BULLET) {
                batch.draw(textures[2], bullet.getX(), bullet.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
            } else {
                batch.draw(textures[3], bullet.getX(), bullet.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
            }
        }

        batch.draw(textures[main.player.numTexture], main.player.getX(), main.player.getY(), DefaultData.spriteSize, DefaultData.spriteSize);

        for (Enemy enemy : main.enemyManager.enemies) {
            if (enemy.getEnemyType() == EnemyType.KAMIKAZE) {
                if (!enemy.isDeathAnimation())
                    batch.draw(enemyType2[enemy.numTexture], enemy.getX(), enemy.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
                else {
                    batch.draw(textures[5], enemy.getX(), enemy.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
                    main.enemyManager.enemies.removeValue(enemy, false);
                }
            }
        }

        for (Enemy enemy : main.enemyManager.enemies) {
            if (enemy.getEnemyType() == EnemyType.SHOOTER_SHIP) {
                if (!enemy.isDeathAnimation())
                    batch.draw(enemyType1[enemy.numTexture], enemy.getX(), enemy.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
                else {
                    batch.draw(textures[5], enemy.getX(), enemy.getY(), DefaultData.spriteSize, DefaultData.spriteSize);
                    main.enemyManager.enemies.removeValue(enemy, false);
                }
            }
        }

        if(main.bossFight) batch.draw(boss[main.bossEnemy.numTexture], main.bossEnemy.getX(), main.bossEnemy.getY(), DefaultData.spriteSize, DefaultData.spriteSize);


        font.draw(batch, main.player.hp + "HP \nScore: " + main.player.score + "\nRecord: " + main.player.record, 10, DefaultData.heightScreen - 10);

        if (main.restarting) {
            batch.draw(textures[4], 0, 0, 500, 900);
        }else if(main.win){
            batch.draw(textures[6], 0, 0, 500, 900);
        }

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
