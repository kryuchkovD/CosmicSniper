package com.samsung.game.CosmicSniper.game.entity.enemy;

import com.samsung.game.CosmicSniper.game.SoundPlayer;
import com.samsung.game.CosmicSniper.game.entity.Entity;

public class Enemy extends Entity {

    private EnemyType enemyType;

    EnemyManager enemyManager;

    public int numTexture = 0;

    private boolean deathAnimation = false;

    public Enemy(int x, int y, EnemyManager enemyManager, EnemyType enemyType) {
        super(x, y);
        this.enemyType = enemyType;
        this.enemyManager=enemyManager;

        enemyManager.enemies.add(this);
    }

    private int timer = 0;
    private int timer1 = 0;

    public void render() {
        super.render();

        timer++;
        if (timer >= 20) {
            timer = 0;
            if (numTexture == 1) {
                numTexture = 0;
            } else {
                numTexture = 1;
            }
        }

        if (enemyType == EnemyType.KAMIKAZE) {
            y -= 7;
        } else {
            timer1++;
            if (timer1 >= 90){
                timer1 = 0;
                enemyManager.createBullet(x);
                SoundPlayer.enemyFire.stop();
                SoundPlayer.enemyFire.play();
            }
                if (numTexture == 0) {
                    x--;
                } else {
                    x++;
                }
        }
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void setEnemyType(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    public boolean isDeathAnimation() {
        return deathAnimation;
    }

    public void setDeathAnimation(boolean deathAnimation) {
        this.deathAnimation = deathAnimation;
    }
}
