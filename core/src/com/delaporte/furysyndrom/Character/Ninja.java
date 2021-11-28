package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public class Ninja extends Character {
    public Ninja(
        Map m, 
        int collisionLayer,
        float xPosition, 
        float yPosition
    ){
        super(
            m,
            collisionLayer,
            125,
            20, 
            20,
            50,
            "Ninja",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../../Assets/Texture/Ninja/Idle__000.png"), 1, 1, 0.1f),
            2.5
        );
    }

    @Override
    public void selectAnimation(){
        this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Ninja/Idle__000.png"), 1, 1, 0.1f);
    }


    public String getType(){
        return "ninja";
    }
}
