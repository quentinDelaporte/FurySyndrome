package com.delaporte.furysyndrom.Character;

import com.delaporte.furysyndrom.Character.Character;
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;
import com.badlogic.gdx.math.Rectangle;
import com.delaporte.furysyndrom.ui.CharacterHealImage;

public class Troll extends Character {
    public Troll(
        Map m, 
        int collisionLayer,
        float xPosition, 
        float yPosition
    ){
        super(
            m,
            collisionLayer,
            1600,
            20, 
            35,
            10,
            "Troll",
            xPosition,
            yPosition,
            64,
            64,
            new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f),
            1.5,
            32,
            32,
            16,
            8,
            new CharacterHealImage("troll_heal_0.png",10,52,128,32)
        );
    }

    @Override
    public void selectAnimation(){
        if(this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.JUMPWALK){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_JUMP.png"), 10, 1, 0.1f);
            else
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_JUMP.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.STATIC){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
            else
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.RUN){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_RUN.png"), 10, 1, 0.1f);
            else 
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_RUN.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.WALK){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_WALK.png"), 10, 1, 0.1f);
            else
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_WALK.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.DEAD){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_DIE.png"), 10, 1, 0.1f);
            else 
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_DIE.png"), 10, 1, 0.1f);
        } else {
            if(this.facing == CharacterFacing.LEFT)
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
            else
                this.animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);     
        }
    }

    public String getType(){
        return "Troll";
    }


}
