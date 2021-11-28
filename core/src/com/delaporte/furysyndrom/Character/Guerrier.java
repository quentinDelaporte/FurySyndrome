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
    public void selectAnimation(){}
    
    public String getType(){
        return "guerrier";
    }
}
