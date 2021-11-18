package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;

public class Mage extends Character {


    public Mage(
        float xPosition, 
        float yPosition
    ){
        super(
            100,
            20, 
            25,
            30,
            "Mage",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../../Assets/Texture/Mage_IDLE.png"), 5, 1, 0.1f),
            1.5
        );
    }

    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(staticCharacterAnimation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);

    }

    @Override
    public void move() {
        if(this.facing == CharacterFacing.LEFT){
            this.xPosition -= 0.8;
        } else {
            this.xPosition += 0.8;
        }
    }
}
