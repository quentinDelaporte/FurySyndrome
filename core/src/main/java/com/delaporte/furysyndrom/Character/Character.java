package com.delaporte.furysyndrom.Character;

import java.util.ArrayList;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.delaporte.furysyndrom.ui.CharacterHealImage;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

public abstract class Character {
    public int hp;
    public final int maxHp;
    public final int strength;
    public final int defence;
    public final int agility;
    public String nom;
    public int collisionLayer;
    public int width;
    public int height;
    private int hitboxHeight;
    private int hitboxWidth;
    private int hitboxOffsetX;
    private int hitboxOffsetY;
    public Rectangle hitbox;
    public float yPosition;
    public float xPosition;
    public float initialY;
    public final double jumpHeight;
    public boolean isAttacking = false;
    public boolean canAttack = true;
    public CharacterEtat etat;
    public CharacterFacing facing;
    public Anim animation;
    public Map m;
    public ArrayList<Character> characters = new ArrayList<Character>();
    private CharacterHealImage characterHealImage;

    public enum CharacterEtat {
        STATIC, 
        JUMPRUN, 
        JUMP, 
        JUMPWALK, 
        WALK, 
        RUN, 
        FALL, 
        FALLWALK, 
        FALLRUN, 
        DEAD;
    }

    public enum CharacterFacing {
        LEFT,
        RIGHT
    }
    
    /**
     * On passe les info de la hitbox car les sprites ne font pas forcement la bonne taille. (certains avec bcp d'espace vide)
     */
    protected Character(
        Map m, 
        int collisionLayer,
        int hp,
        int strength, 
        int defence, 
        int agility, 
        String nom, 
        float xPosition, 
        float yPosition, 
        int width, 
        int height, 
        Anim animation, 
        double jumpHeight,
        int hitboxWidth,
        int hitboxHeight, 
        int hitboxOffsetX, 
        int hitboxOffsetY,
        CharacterHealImage characterHealImage
    ){
        this.m = m;
        this.collisionLayer = collisionLayer;
        this.hp = hp;
        this.maxHp = hp;
        this.defence = defence;
        this.strength = strength;
        this.agility = agility;
        this.nom = nom;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle((int)(xPosition + hitboxOffsetX), (int) (yPosition + hitboxOffsetY), (int) hitboxWidth, (int) hitboxHeight);
        this.etat = CharacterEtat.STATIC;
        this.facing = CharacterFacing.LEFT;
        this.animation = animation;
        this.jumpHeight = jumpHeight;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.hitboxOffsetX = hitboxOffsetX;
        this.hitboxOffsetY = hitboxOffsetY;
        this.characterHealImage = characterHealImage;
    }

    protected abstract void selectAnimation();

    public String getType(){
        return "undefined";
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


    public void setCharacterEtatSTATIC(){
        this.etat = CharacterEtat.STATIC;
    }

    public void setCharacterEtatWALK(){
        this.etat = CharacterEtat.WALK;
    }

    public void setCharacterEtatRUN(){
        this.etat = CharacterEtat.RUN;
    }

    public void draw(SpriteBatch batch, float stateTime) {
        //Si besoin de débug: 
        // drawHitbox(batch, getAttackHitbox(), Color.RED);
        // drawHitbox(batch, hitbox, Color.GREEN);
        selectAnimation();
        batch.draw(animation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int)(xPosition + hitboxOffsetX), (int) (yPosition + hitboxOffsetY), (int) hitboxWidth, (int) hitboxHeight);
        characterHealImage.draw(batch, stateTime);
    }

	public boolean isJumping(){
        return (this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.JUMPRUN);
    }

    public boolean isFalling(){
        return (this.etat == CharacterEtat.FALL || this.etat == CharacterEtat.FALLWALK || this.etat == CharacterEtat.FALLRUN);
    }

    public void setCharacterEtatJUMP(){
        if(
            this.etat != CharacterEtat.JUMP &&
            this.etat != CharacterEtat.JUMPWALK &&
            this.etat != CharacterEtat.JUMPRUN
        ){
            this.etat = CharacterEtat.JUMP;
            initialY = yPosition;
        } else if(this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.JUMPRUN) {
            this.etat = CharacterEtat.JUMP;
        }
    }

    /**
     * Déplace le personnage.
     * gere la vitesse de déplacement sur X:
     * Marcher: 1 + agilité/100
     * Courir: 2.4 + agilité/100
     * Statique : 0
     * Lui applique la gravité et gere les saut
     */
    public void move() {
        if(isAttacking){
            attaquer(characters);
            isAttacking = false;
        }
        double movespeed = 0;
        if((this.etat == CharacterEtat.WALK || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.FALLWALK)){
            movespeed = 1 + (this.agility/100);
        } else if(this.etat == CharacterEtat.RUN || this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.FALLRUN ){
            movespeed = 2.4 + (this.agility/100);
        } else {
            movespeed = 0;
        }
        if(this.facing == CharacterFacing.RIGHT){
            if(!detectCollision((float)(xPosition+movespeed), yPosition+4, width, height)){
                this.xPosition += movespeed;
                hitbox.x += movespeed;
            } else if(!detectCollision((float)(xPosition+movespeed), yPosition+2, width, height)){
                this.xPosition += movespeed;
                this.yPosition +=2;
                hitbox.x += movespeed;
                hitbox.y +=2;
            } else if(!detectCollision((float)(xPosition+movespeed), yPosition+4, width, height)){
                this.xPosition += movespeed;
                this.yPosition +=4;
                hitbox.x += movespeed;
                hitbox.y +=4;
            } else if(!detectCollision((float)(xPosition+movespeed), yPosition+8, width, height)){
                this.xPosition += movespeed;
                this.yPosition +=8;
                hitbox.x += movespeed;
                hitbox.y +=8;
            }
        } else {
            if(!detectCollision((float)(xPosition-movespeed), yPosition, width, height)){
                this.xPosition -= movespeed;
                hitbox.x -= movespeed;
            } else if(!detectCollision((float)(xPosition-movespeed), yPosition+2, width, height)){
                this.xPosition -= movespeed;
                this.yPosition +=2;
                hitbox.x -= movespeed;
                hitbox.y +=2;
            } else if(!detectCollision((float)(xPosition-movespeed), yPosition+4, width, height)){
                this.xPosition -= movespeed;
                this.yPosition +=4;
                hitbox.x -= movespeed;
                hitbox.y +=4;
            } else if(!detectCollision((float)(xPosition-movespeed), yPosition+8, width, height)){
                this.xPosition -= movespeed;
                this.yPosition +=8;
                hitbox.x -= movespeed;
                hitbox.y +=8;
            }

        }

        if(this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.JUMPRUN){
            jump();
        }
        if(this.etat !=CharacterEtat.JUMP && this.etat != CharacterEtat.JUMPRUN && this.etat != CharacterEtat.JUMPWALK){
            appliquerGravite();
        }
    }

    public void appliquerGravite(){
        if(!this.isJumping()){
            if(!detectCollision(xPosition, yPosition-4, width, height)){
                this.etat = CharacterEtat.FALL;
                hitbox.y -= 4;
                this.yPosition -= 4;
            } else {
                this.etat = CharacterEtat.STATIC;
    
            }
        }
    }

    public boolean detectCollision(float x, float y, int w, int h) {
        Rectangle futurHitbox = new Rectangle((int) (x+0.25*w), (int)(y+0.25*h), (int) (0.5*w), (int) (0.5*h));   
        MapObjects collisionObjects = m.getCollisionTile(collisionLayer);
		for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, futurHitbox))
				return true;
		}
		for (PolygonMapObject polygonObject : collisionObjects.getByType(PolygonMapObject.class)) {
			Polygon polygon = polygonObject.getPolygon();
			Polygon hitboxPolygon = new Polygon(new float[] { (int) (x+0.25*w), (int)(y+0.25*h), (int) (x+0.25*w) + (int)(0.25*w), (int)(y+0.25*h),
                (int) (x+0.25*w) + (int)(0.25*w), (int)(y+0.25*h) + (int)(0.25*h), (int) (x+0.25*w), (int)(y+0.25*h) + (int)(0.25*h) });

			if (Intersector.overlapConvexPolygons(polygon, hitboxPolygon)) {
				return true;
			}
		}
        return false;
	}

    public void fall(){
        if(yPosition > initialY) {
            appliquerGravite();
        } else {
            this.etat = CharacterEtat.STATIC;
        }
    }

    /**
     * Gére le saut
     * Si le saut est effectué a moins de 30%: on saute de 4 u / tick
     * Si le saut est effectué a moins de 50%: on saute de 2 u / tick
     * Si le saut est effectué a moins de 70%: on saute de 1 u / tick
     * Sinon on saute de 0.5 u / tick
     */
    public void jump(){
        if(yPosition < initialY + ((jumpHeight * 32) * 0.3)) {
            if(!detectCollision(xPosition, yPosition+4, width, height)){
                this.yPosition += 4; 
                hitbox.y += 4;
            } else {
                this.etat = CharacterEtat.FALL;
            }
        } else if(yPosition < initialY + ((jumpHeight * 32) * 0.5)) {
            if(!detectCollision(xPosition, yPosition+2, width, height)){
                this.yPosition += 2; 
                hitbox.y += 2;
            } else {
                this.etat = CharacterEtat.FALL;
            }
        } else  if(yPosition < initialY + ((jumpHeight * 32) * 0.7)) {
            if(!detectCollision(xPosition, yPosition+1, width, height)){
                this.yPosition += 1; 
                hitbox.y += 1;
            } else {
                this.etat = CharacterEtat.FALL;
            }
        } else if(yPosition < initialY + (jumpHeight * 32)) {
            if(!detectCollision(xPosition, (float)(yPosition+0.5), width, height)){
                this.yPosition += 0.5;
                hitbox.y += 0.5;
            } else {
                this.etat = CharacterEtat.FALL;
            }
        } else if(yPosition >= initialY + (jumpHeight * 32)) {
            this.etat = CharacterEtat.FALL;
        }
    }

    public void setCharacterEtatJUMPRUN(){
        if(
            this.etat != CharacterEtat.JUMP &&
            this.etat != CharacterEtat.JUMPWALK &&
            this.etat != CharacterEtat.JUMPRUN
        ){
            this.etat = CharacterEtat.JUMPRUN;
            initialY = yPosition;
        } else if(this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPWALK) {
            this.etat = CharacterEtat.JUMPRUN;
        }
    }

    public void setCharacterEtatFALLRUN(){
        this.etat = CharacterEtat.FALLRUN;
    }

    public void setCharacterEtatFALLWALK(){
        this.etat = CharacterEtat.FALLWALK;
    }

    public void setCharacterEtatFALLSTATIC(){
        this.etat = CharacterEtat.FALL;
    }

    public void setCharacterEtatJUMPWALK(){
        if(
            this.etat != CharacterEtat.JUMP &&
            this.etat != CharacterEtat.JUMPWALK &&
            this.etat != CharacterEtat.JUMPRUN
        ){
            this.etat = CharacterEtat.JUMPWALK;
            initialY = yPosition;
        } else if(this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPRUN) {
            this.etat = CharacterEtat.JUMPWALK;
        }
    }

    public void setCharacterEtatRUNWALK(){
        this.etat = CharacterEtat.FALLRUN;
    }

    public void setStatic(){
        this.etat = CharacterEtat.STATIC;
    }

    public int getHp(){
        return hp;
    }

    public double getHpPercent(){
        return this.hp/maxHp *100;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }

    public int getMaxHp(){
        return maxHp;
    }

    public Rectangle getAttackHitbox(){
        return new Rectangle(
            (int)(xPosition + (facing == CharacterFacing.LEFT ? -(hitboxWidth/2 - hitboxOffsetX) : (hitboxWidth/2 + hitboxOffsetX))) , 
            (int)(yPosition + (hitboxHeight/2 + hitboxOffsetY)), 
            (int)(hitboxWidth), 
            (int)(0.5*hitboxHeight)
        );
    }

    public void isAttacking(){
        isAttacking = true;
    }

    public void setCharacters(ArrayList<Character> c){
        characters = c;
    }

    /**
     * Quand une attaque est lancé on ne peux plus attaquer pendant 0.45s
     */
    public void attaquer(ArrayList<Character> characters){
        Rectangle attackHitbox = getAttackHitbox();
        if(canAttack){
            for(Character c : characters){
                if(c != this){
                    if(attackHitbox.overlaps(c.getHitbox())){
                        c.getDamaged(this.strength);
                    }
                }
            }
            canAttack = false;
            Timer.schedule(new Task() {
                @Override
                public void run() {
                    canAttack = true;
                }
            },0.45f);
        }
    }

    public void getDamaged(int damageHp){
        this.hp -= (int)((1.0 - ((float) this.defence/100.0)) * (float)damageHp);
        characterHealImage.setHealBarPercent(hp, maxHp);
    }

    public void drawHitbox(SpriteBatch batch, Rectangle hitbox, Color color){
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, width, height);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(texture, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }
}
