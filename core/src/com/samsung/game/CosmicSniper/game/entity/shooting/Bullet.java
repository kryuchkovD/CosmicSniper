package com.samsung.game.CosmicSniper.game.entity.shooting;

import com.samsung.game.CosmicSniper.game.DefaultData;
import com.samsung.game.CosmicSniper.game.entity.Entity;

public class Bullet extends Entity {

    private BulletType bulletType;

    public Bullet(int x, int y, BulletType bulletType, BulletManager bulletManager) {
        super(x, y);
        this.bulletType = bulletType;
        bulletManager.bullets.add(this);
    }

    protected void setUpHitBox(){
        float i = DefaultData.spriteSize/3f;
        hitBox.set(x+i, y+i, DefaultData.spriteSize/1.5f, DefaultData.spriteSize/1.5f);
    }

    public void render(){
        super.render();
        if(bulletType == BulletType.PLAYER_BULLET) y+=10;
        else y-=10;
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public void setBulletType(BulletType bulletType){
        this.bulletType=bulletType;
    }
}
