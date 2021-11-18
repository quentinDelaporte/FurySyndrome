package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.delaporte.furysyndrom.Anim;

public abstract class Character {
    public int hp;
    public final int maxHp;
    public final int strength;
    public final int defense;
    public final int agility;
    public int additionalStrength;
    public int additionalDefense;
    public int additionalAgility;
    public String nom;
    public float yPosition;
    public float xPosition;
    public int width;
    public int height;
    public Rectangle hitbox;
    public CharacterEtat etat;
    public CharacterFacing facing;
    public final Anim staticCharacterAnimation;
    public final double jumpHeight;

    public enum CharacterEtat {
        STATIC, JUMPRUN, JUMP, JUMPWALK, WALK, RUN, FALL, FALLWALK, FALLRUN, DEAD;
    }

    public enum CharacterFacing {
        LEFT, RIGHT
    }
    
    public Character(
        int hp,
        int strength, 
        int defense, 
        int agility, 
        String nom, 
        float xPosition, 
        float yPosition, 
        int width, 
        int height, 
        Anim staticCharacterAnimation, 
        double jumpHeight
    ){
        this.hp = hp;
        this.maxHp = hp;
        this.defense = defense;
        this.strength = strength;
        this.agility = agility;
        this.additionalStrength = 0;
        this.additionalDefense = 0;
        this.additionalAgility = 0;
        this.nom = nom;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);
        this.etat = CharacterEtat.STATIC;
        this.facing = CharacterFacing.LEFT;
        this.staticCharacterAnimation = staticCharacterAnimation;
        this.jumpHeight = jumpHeight;
    }

    public boolean isDead() {
        if (yPosition < 0 || hp < 1) {
            etat = CharacterEtat.DEAD;
            return true;
        }
        return false;
    }

    public void deplacerX(float deltaX){
        this.xPosition += deltaX;
    }

    public void deplacerY(float deltaY){
        this.yPosition += deltaY;
    }

    public void setFacingToLeft(){
        this.facing = CharacterFacing.LEFT;
    }
    
    public void setFacingToRight(){
        this.facing = CharacterFacing.RIGHT;
    }

    protected abstract void draw(SpriteBatch batch, float stateTime);

    protected abstract void move();

    public void setAdditionalStrength(int additionalStrength){
        this.additionalStrength = additionalStrength;
    }

    public int getAdditionalStrength(){
        return this.additionalStrength;
    }
    
    public void setAdditionalDefense(int additionalDefense){
        this.additionalDefense = additionalDefense;
    }

    public int getAdditionalDefense(){
        return this.additionalDefense;
    }
    
    public void setAdditionalAgility(int additionalAgility){
        this.additionalAgility = additionalAgility;
    }
    
    public int getAdditionalAgility(){
        return this.additionalAgility;
    }
    
}
