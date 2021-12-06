package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

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
            120,
            30, 
            25,
            10,
            "Troll",
            xPosition,
            yPosition,
            32,
            32,
            new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f),
            1.5
        );
    }

    @Override
    public void selectAnimation(){
        if(this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.JUMPWALK){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_JUMP.png"), 10, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_JUMP.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.STATIC){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.RUN){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_RUN.png"), 10, 1, 0.1f);
            else 
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_RUN.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.WALK){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_WALK.png"), 10, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_WALK.png"), 10, 1, 0.1f);
        } else if(this.etat == CharacterEtat.DEAD){
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_DIE.png"), 10, 1, 0.1f);
            else 
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_DIE.png"), 10, 1, 0.1f);
        } else {
            if(this.facing == CharacterFacing.LEFT)
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);
            else
                this.Animation = new Anim(Gdx.files.internal("../../Assets/Texture/Troll_R_IDLE.png"), 10, 1, 0.1f);     
        }
    }

    public String getType(){
        return "Troll";
    }

}
