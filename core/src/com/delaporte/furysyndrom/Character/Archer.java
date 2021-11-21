package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public class Archer extends Character {
    public Archer(
        Map m, 
        int collisionLayer,
        float xPosition, 
        float yPosition
    ){
        super(
            m, 
            collisionLayer,
            100,
            20, 
            25,
            30,
            "Archer",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../Assets/Texture/Mage_IDLE.png"), 5, 1, 0.1f),
            1.2
        );
    }

    @Override
    public void selectAnimation(){}

}
