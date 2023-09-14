package com.samsung.game.CosmicSniper.game.entity;

import com.badlogic.gdx.math.Rectangle;
import com.samsung.game.CosmicSniper.game.DefaultData;

public class Entity {

    protected Rectangle hitBox;

    protected int x;
    protected int y;

    public Entity(int x, int y){
        this.x=x;
        this.y=y;
        hitBox = new Rectangle();
        setUpHitBox();
    }

    public void render(){
        setUpHitBox();
    }

    protected void setUpHitBox(){
        float i = DefaultData.spriteSize/4f;
        hitBox.set(x+i, y+i, DefaultData.spriteSize/2f, DefaultData.spriteSize/2f);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
