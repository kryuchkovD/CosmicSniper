package com.samsung.game.CosmicSniper.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundPlayer {

    public static final Music playerFire = Gdx.audio.newMusic(Gdx.files.internal("sounds/firePlayer.mp3"));
    public static final Music enemyFire = Gdx.audio.newMusic(Gdx.files.internal("sounds/fireEnemy.mp3"));
    public static final Music enemyDie = Gdx.audio.newMusic(Gdx.files.internal("sounds/enemyDie.mp3"));
    public static final Music gameOver = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameOver.mp3"));
    public static final Music win = Gdx.audio.newMusic(Gdx.files.internal("sounds/win.mp3"));

    public static void setVolume(float volume){
        playerFire.setVolume(volume);
        enemyFire.setVolume(volume);
        enemyDie.setVolume(volume);
        gameOver.setVolume(volume);
        win.setVolume(volume);
    }

    public static void stopAll(){
        playerFire.stop();
        enemyFire.stop();
        enemyDie.stop();
    }

}
