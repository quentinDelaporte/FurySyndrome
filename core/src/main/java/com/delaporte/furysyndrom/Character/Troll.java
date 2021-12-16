package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;
import com.delaporte.furysyndrom.ui.CharacterHealImage;

public class Troll extends Character {

    private Anim trollLJump = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_L_JUMP.png"), 10, 1, 0.1f);
    private Anim trollRJump = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_JUMP.png"), 10, 1, 0.1f);
    private Anim trollLIdle = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_L_IDLE.png"), 10, 1, 0.1f);
    private Anim trollRIdle = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_IDLE.png"), 10, 1, 0.1f);
    private Anim trollLRun = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_L_RUN.png"), 10, 1, 0.1f);
    private Anim trollRRun = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_RUN.png"), 10, 1, 0.1f);
    private Anim trollLWalk = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_L_WALK.png"), 10, 1, 0.1f);
    private Anim trollRWalk = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_WALK.png"), 10, 1, 0.1f);
    private Anim trollLDie = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_L_DIE.png"), 10, 1, 0.1f);
    private Anim trollRDie = new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_DIE.png"), 10, 1, 0.1f);
    
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
            new Anim(Gdx.files.internal("../assets/Texture/CHARACTERS/TROLL/Troll_R_IDLE.png"), 10, 1, 0.1f),
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
                this.animation = trollLJump;
            else
                this.animation =trollRJump;
        } else if(this.etat == CharacterEtat.STATIC){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = trollLIdle;
            else
                this.animation = trollRIdle;
        } else if(this.etat == CharacterEtat.RUN){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = trollLRun;
            else 
                this.animation = trollRRun;
        } else if(this.etat == CharacterEtat.WALK){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = trollLWalk;
            else
                this.animation = trollRWalk;
        } else if(this.etat == CharacterEtat.DEAD){
            if(this.facing == CharacterFacing.LEFT)
                this.animation = trollLDie;
            else 
                this.animation = trollRDie;
        } else {
            if(this.facing == CharacterFacing.LEFT)
                this.animation = trollLIdle;
            else
                this.animation = trollRIdle;     
        }
    }

    public String getType(){
        return "Troll";
    }


}
