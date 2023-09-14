package com.samsung.game.CosmicSniper.game.entity.shooting;

import com.badlogic.gdx.utils.Array;
import com.samsung.game.CosmicSniper.game.DefaultData;

public class BulletManager {

    public Array<Bullet> bullets = new Array<>();

    public void createBullet(BulletType bulletType, int x, int y){
        new Bullet(x, y, bulletType, this);
    }

    public void render(){
        for (Bullet bullet : bullets) {
            bullet.render();
            if(bullet.getY() > DefaultData.heightScreen || bullet.getY() < -DefaultData.spriteSize){
                bullets.removeValue(bullet, false);
            }
        }
    }
}
