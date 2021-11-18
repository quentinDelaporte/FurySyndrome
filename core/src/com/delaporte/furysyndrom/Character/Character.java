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
    public float initialY;

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

    public void draw(SpriteBatch batch, float stateTime) {
        batch.draw(staticCharacterAnimation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);
    }

	public boolean isJumping(){
        return (this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.JUMPRUN);
    }

    public void setJumping(){
        if(
            this.etat != CharacterEtat.JUMP &&
            this.etat != CharacterEtat.JUMPWALK &&
            this.etat != CharacterEtat.JUMPRUN
        ){
            this.etat = CharacterEtat.JUMP;
        }
        initialY = yPosition;
    }

    public void move() {
        double movespeed = 0;
        if(
            this.etat == CharacterEtat.WALK || 
            this.etat == CharacterEtat.JUMPWALK || 
            this.etat == CharacterEtat.FALLWALK
        ){
            movespeed = 1 + (this.agility/100);
        } else if(
            this.etat == CharacterEtat.RUN || 
            this.etat == CharacterEtat.JUMPRUN || 
            this.etat == CharacterEtat.FALLRUN
        ){
            movespeed = 1.7 + (this.agility/100);
        } else {
            movespeed = 0;
        }
        if(this.facing == CharacterFacing.LEFT){
            this.xPosition += movespeed;
        } else {
            this.xPosition -= movespeed;
        }
        if(this.etat == CharacterEtat.JUMP){
            if(yPosition < initialY + ((jumpHeight * 32) * 0.3)) {
                this.yPosition += 4; 
            } else if(yPosition < initialY + ((jumpHeight * 32) * 0.5)) {
                this.yPosition += 2; 
            } else  if(yPosition < initialY + ((jumpHeight * 32) * 0.7)) {
                this.yPosition += 1; 
            } else if(yPosition < initialY + (jumpHeight * 32)) {
                this.yPosition += 0.5;
            } else {
                this.etat = CharacterEtat.FALL;
            }

        }
    }

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
