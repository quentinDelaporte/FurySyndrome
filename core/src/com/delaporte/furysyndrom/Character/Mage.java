package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public class Mage extends Character {
    public Mage(
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
    public void selectAnimation(){
        if(this.etat == CharacterEtat.JUMPRUN){
            this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_JUMP.png"), 5, 1, 0.1f);
        } else {
            this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_IDLE.png"), 5, 1, 0.1f);
        }
    }


}
