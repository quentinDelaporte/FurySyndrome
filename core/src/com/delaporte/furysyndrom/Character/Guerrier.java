package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public class Guerrier extends Character {
    public Guerrier(        
        Map m, 
        int collisionLayer,
        float xPosition, 
        float yPosition
    ){
        super(
            m,
            collisionLayer,
            150,
            10, 
            50,
            15,
            "Guerrier",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../Assets/Texture/Mage_IDLE.png"), 5, 1, 0.1f),
            1
        );
    }
    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(staticCharacterAnimation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);

    }

    @Override
    public void move(Map map, MapObjects collisionLayer) {
        if(this.facing == CharacterFacing.LEFT){
            this.xPosition -= 0.8;
        } else {
            this.xPosition += 0.8;
        }
    }

}
