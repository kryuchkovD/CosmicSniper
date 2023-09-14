package com.samsung.game.CosmicSniper.game.entity.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.DefaultData;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletType;

public class EnemyManager {

    public Array<Enemy> enemies = new Array<>();

    Main main;

    public EnemyManager(Main main) {
        this.main = main;
    }

    private int timer = 0;

    public void render() {
        timer++;

        if (timer >= 40/((main.player.score/5)+1)) {
            timer = 0;
            int r = MathUtils.random(1, 2);

            if (r == 1) {
                int r1 = MathUtils.random(1, 3);
                int x = MathUtils.random(0, DefaultData.widthScreen - DefaultData.spriteSize);
                if (r1 == 1) {
                    new Enemy(x, DefaultData.heightScreen - DefaultData.spriteSize*2, this, EnemyType.SHOOTER_SHIP);
                } else {
                    new Enemy(x, DefaultData.heightScreen, this, EnemyType.KAMIKAZE);
                }
            }
        }

        for (Enemy enemy : enemies) {
            enemy.render();
            if(enemy.getY()+DefaultData.spriteSize<0){
                enemies.removeValue(enemy, false);
            }
        }
    }

    public void createBullet(int x){
        main.bulletManager.createBullet(BulletType.ENEMY_BULLET, x, DefaultData.heightScreen - DefaultData.spriteSize*2 - DefaultData.spriteSize/2);
    }

}
