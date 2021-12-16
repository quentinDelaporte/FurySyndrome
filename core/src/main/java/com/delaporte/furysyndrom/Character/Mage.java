package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;
import com.delaporte.furysyndrom.ui.CharacterHealImage;

public class Mage extends Character {

    public Anim mageLJump = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_L_JUMP.png"), 5, 1, 0.1f);
    public Anim mageRJump = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_JUMP.png"), 5, 1, 0.1f);
    public Anim mageLIdle = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_L_IDLE.png"), 5, 1, 0.1f);
    public Anim mageRIdle = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_IDLE.png"), 5, 1, 0.1f);
    public Anim mageLRun = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_L_RUN.png"), 5, 1, 0.1f);
    public Anim mageRRun = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_RUN.png"), 5, 1, 0.1f);
    public Anim mageLWalk = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_L_WALK.png"), 5, 1, 0.1f);
    public Anim mageRWalk = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_WALK.png"), 5, 1, 0.1f);
    public Anim mageLDie = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_L_DIE.png"), 5, 1, 0.1f);
    public Anim mageRDie = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_DIE.png"), 5, 1, 0.1f);

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
            new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/WIZARD/Mage_R_IDLE.png"), 5, 1, 0.1f),
            1.5,
            32,
            32, 
            0,
            0,
            new CharacterHealImage("mage_heal_0.png",10,10,128,32)
        );
    }

    @Override
    public void selectAnimation(){
        if(this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.JUMPWALK){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLJump;
            else
                this.animation = mageRJump;
        } else if(this.etat == CharacterEtat.STATIC){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLIdle;
            else
                this.animation = mageRIdle;
        } else if(this.etat == CharacterEtat.RUN){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLRun;
            else 
                this.animation = mageRRun;
        } else if(this.etat == CharacterEtat.WALK){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLWalk;
            else
                this.animation = mageRWalk;
        } else if(this.etat == CharacterEtat.DEAD){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLDie;
            else 
                this.animation = mageRDie;
        } else {
            if(this.facing == CharacterFacing.LEFT)
                this.animation = mageLIdle;
            else
                this.animation = mageRIdle;     
        }
    }

    public String getType(){
        return "mage";
    }




}
