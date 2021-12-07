package com.delaporte.furysyndrom.Character;

import com.delaporte.furysyndrom.Character.Character;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;
import com.badlogic.gdx.math.Rectangle;

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
            1000,
            20, 
            25,
            30,
            "Mage",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_IDLE.png"), 5, 1, 0.1f),
            1.5,
            32,
            32
        );
    }

    @Override
    public void selectAnimation(){
        if(this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.JUMPWALK){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_L_JUMP.png"), 5, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_JUMP.png"), 5, 1, 0.1f);
        } else if(this.etat == CharacterEtat.STATIC){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_L_IDLE.png"), 5, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_IDLE.png"), 5, 1, 0.1f);
        } else if(this.etat == CharacterEtat.RUN){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_L_RUN.png"), 5, 1, 0.1f);
            else 
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_RUN.png"), 5, 1, 0.1f);
        } else if(this.etat == CharacterEtat.WALK){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_L_WALK.png"), 5, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_WALK.png"), 5, 1, 0.1f);
        } else if(this.etat == CharacterEtat.DEAD){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_Lss_DIE.png"), 5, 1, 0.1f);
            else 
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_DIE.png"), 5, 1, 0.1f);
        } else {
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_L_IDLE.png"), 5, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Mage_R_IDLE.png"), 5, 1, 0.1f);     
        }
    }

    public String getType(){
        return "mage";
    }




}
