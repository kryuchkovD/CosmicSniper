package com.samsung.game.CosmicSniper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.samsung.game.CosmicSniper.game.ActionHandler;
import com.samsung.game.CosmicSniper.game.Background;
import com.samsung.game.CosmicSniper.game.DefaultData;
import com.samsung.game.CosmicSniper.game.Drawer;
import com.samsung.game.CosmicSniper.game.SoundPlayer;
import com.samsung.game.CosmicSniper.game.entity.Player;
import com.samsung.game.CosmicSniper.game.entity.enemy.Boss;
import com.samsung.game.CosmicSniper.game.entity.enemy.EnemyManager;
import com.samsung.game.CosmicSniper.game.entity.shooting.BulletManager;

public class Main extends ApplicationAdapter {

	OrthographicCamera camera;

	public Player player;
	public Drawer drawer;
	public BulletManager bulletManager;
	public EnemyManager enemyManager;
	public Background background;
	public ActionHandler actionHandler;
	public Boss bossEnemy;

	FileHandle record;

	//статусы
	public boolean restarting = false;
	public boolean boss = false;
	public boolean win = false;
	public boolean infinityMode = false;

	private boolean isPlayedMusic = false;
	public boolean bossFight = false;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(DefaultData.widthScreen, DefaultData.heightScreen);

		player = new Player(DefaultData.widthScreen/2 - DefaultData.spriteSize/2, DefaultData.spriteSize*2, this);
		drawer = new Drawer(this);
		bulletManager = new BulletManager();
		enemyManager = new EnemyManager(this);
		background = new Background();
		actionHandler = new ActionHandler(this);
		bossEnemy = new Boss(-DefaultData.spriteSize*2, DefaultData.heightScreen - DefaultData.spriteSize*2, this);

		SoundPlayer.setVolume(0.5f);

		// сохранение рекорда
		record = Gdx.files.local("record.txt");
		if(!record.exists()){
			record.writeString("0", false);
		}
		player.record = Integer.parseInt(record.readString());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		if(!restarting && !boss && !win) {
			player.render();
			bulletManager.render();
			if(!bossFight) enemyManager.render();
			actionHandler.render();
			background.render();
		}else{
			if(Gdx.input.justTouched()){
				restarting();
				restarting=false;
				win=false;
			}
		}
		if(bossFight && !win) {
			bossEnemy.render();
		}
		background.draw();
		drawer.render();
		if(win){
			SoundPlayer.stopAll();
			if(!isPlayedMusic) SoundPlayer.win.play();
			isPlayedMusic = true;
		}
		if(player.hp<=0){
			player.hp=0;
			restarting=true;
			SoundPlayer.stopAll();
			if(!isPlayedMusic) SoundPlayer.gameOver.play();
			isPlayedMusic=true;
			record.writeString(player.score + "", false);
		}
		if(player.score>25 && !bossFight && !infinityMode){
			bossAppear();
			bossFight=true;
		}

		setUpCamera();
	}
	
	@Override
	public void dispose () {
		drawer.dispose();
		background.dispose();
	}

	private void restarting(){
		bulletManager.bullets.clear();
		enemyManager.enemies.clear();
		infinityMode = win;
		if(!infinityMode) {
			player.hp=10;
			if(player.score > player.record){
				player.record=player.score;
			}
			player.score=0;
		}
		player.touchHandler.setX(Gdx.graphics.getWidth() / 2 - DefaultData.spriteSize / 2);
		SoundPlayer.gameOver.stop();
		SoundPlayer.win.stop();
		isPlayedMusic=false;
		win = false;
		bossFight = false;
		boss = false;
		bossEnemy.hp = 5;
	}

	private void bossAppear(){
		bulletManager.bullets.clear();
		enemyManager.enemies.clear();
		player.hp=10;
		SoundPlayer.stopAll();
	}

	private void setUpCamera() {
		drawer.getBatch().setProjectionMatrix(camera.combined);
		background.getBatch().setProjectionMatrix(camera.combined);
		camera.position.set(new Vector3(DefaultData.widthScreen / 2f, DefaultData.heightScreen / 2f, 0));
		camera.update();
	}
}
