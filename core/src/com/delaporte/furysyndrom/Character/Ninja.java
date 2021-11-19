package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public class Ninja extends Character {
    public Ninja(
        float xPosition, 
        float yPosition
    ){
        super(
            125,
            20, 
            20,
            50,
            "Ninja",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../Assets/Texture/Mage_IDLE.png"), 5, 1, 0.1f),
            2.5
        );
    }
    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(staticCharacterAnimation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);

    }



}
