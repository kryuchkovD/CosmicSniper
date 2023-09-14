package com.samsung.game.CosmicSniper.game;

import com.samsung.game.CosmicSniper.Main;
import com.samsung.game.CosmicSniper.game.entity.enemy.Enemy;
import com.samsung.game.CosmicSniper.game.entity.enemy.EnemyType;
import com.samsung.game.CosmicSniper.game.entity.shooting.Bullet;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletType;

public class ActionHandler {

    Main main;

    public ActionHandler(Main main) {
        this.main = main;
    }

    private int hits = 0;

    public void render() {
        for (Bullet bullet : main.bulletManager.bullets) {
            if (bullet.getBulletType() == BulletType.ENEMY_BULLET) {
                if (main.player.getHitBox().overlaps(bullet.getHitBox())) {
                    hits++;
                    bullet.setBulletType(BulletType.NOT_DANGER);
                    main.player.hp--;
                }
            }
        }
        for (Enemy enemy : main.enemyManager.enemies) {
            if (enemy.getEnemyType() == EnemyType.KAMIKAZE) {
                if (enemy.getHitBox().overlaps(main.player.getHitBox())) {
                    hits++;
                    enemy.setDeathAnimation(true);
                    enemy.setEnemyType(EnemyType.NOT_DANGER);
                    main.player.hp--;
                    SoundPlayer.enemyDie.stop();
                    SoundPlayer.enemyDie.play();
                }
            } else {
                for (Bullet bullet : main.bulletManager.bullets) {
                    if (bullet.getBulletType() == BulletType.PLAYER_BULLET) {
                        if (enemy.getHitBox().overlaps(bullet.getHitBox())) {
                            enemy.setDeathAnimation(true);
                            main.player.score++;
                            SoundPlayer.enemyDie.stop();
                            SoundPlayer.enemyDie.play();
                        }
                    }
                }
            }
        }

        for (Bullet bullet : main.bulletManager.bullets) {
            if (bullet.getBulletType() == BulletType.PLAYER_BULLET) {
                if (main.bossEnemy.getHitBox().overlaps(bullet.getHitBox()) && main.bossFight) {
                    main.bulletManager.bullets.removeValue(bullet, false);
                    main.bossEnemy.hp--;
                    if(main.bossEnemy.hp<=0){
                        main.bossFight=false;
                        main.boss=false;
                        main.win=true;
                    }
                    SoundPlayer.enemyDie.stop();
                    SoundPlayer.enemyDie.play();
                }
            }
        }

        boolean isHitting = hits != 0;

        if (!isHitting) {
            for (Bullet bullet : main.bulletManager.bullets) {
                if (bullet.getBulletType() == BulletType.NOT_DANGER) {
                    main.bulletManager.bullets.removeValue(bullet, false);
                }
            }
            for (Enemy enemy : main.enemyManager.enemies) {
                if (enemy.getEnemyType() == EnemyType.NOT_DANGER) {
                    main.enemyManager.enemies.removeValue(enemy, false);
                }
            }
        }
        hits = 0;
    }

}
