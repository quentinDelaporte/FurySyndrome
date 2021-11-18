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


}
