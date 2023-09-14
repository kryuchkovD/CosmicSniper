package com.samsung.game.CosmicSniper.game.entity.enemy;

import com.badlogic.gdx.math.MathUtils;
import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.DefaultData;
import com.samsung.game.CosmicSniper.game.SoundPlayer;
import com.samsung.game.CosmicSniper.game.entity.Entity;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletType;

public class Boss extends Entity {
    Main main;

    private boolean side = false; //false = лево , true = право

    public int hp = 3;

    public int numTexture = 0;
    public Boss(int x, int y, Main main) {
        super(x, y);
        this.main=main;
    }

    private int timer = 0;
    private int timer1 = 0;

    public void setUpHitBox(){
        float i = DefaultData.spriteSize/12f;
        hitBox.set(x+i, y+i, DefaultData.spriteSize/6f, DefaultData.spriteSize/6f);
    }

    public void render(){
        super.render();
        if (side) {
            this.x -= 20;
        } else {
            this.x += 20;
        }

        timer++;
        timer1++;

        if(timer1>=45){
            timer1=0;
            side = !side;
        }

        if(timer>=17) {
            numTexture=0;
            timer=0;
            int r = MathUtils.random(1, 3);

            if (r == 1 || r == 2) {
                numTexture=1;
                main.bulletManager.createBullet(BulletType.ENEMY_BULLET, x - DefaultData.spriteSize, y - DefaultData.spriteSize);
                SoundPlayer.enemyFire.stop();
                SoundPlayer.enemyFire.play();
                main.bulletManager.createBullet(BulletType.ENEMY_BULLET, x + DefaultData.spriteSize, y - DefaultData.spriteSize);
                SoundPlayer.enemyFire.stop();
                SoundPlayer.enemyFire.play();
            }
        }
    }

}
