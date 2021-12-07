package com.delaporte.furysyndrom.Character;

import java.util.ArrayList;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public abstract class Character {
    public int hp;
    public final int maxHp;
    public final int strength;
    public final int defence;
    public final int agility;
    public int additionalStrength;
    public int additionaldefence;
    public int additionalAgility;
    public String nom;
    public float yPosition;
    public float xPosition;
    public int width;
    public int height;
    public Rectangle hitbox;
    public CharacterEtat etat;
    public CharacterFacing facing;
    public Anim Animation;
    public final double jumpHeight;
    public float initialY;
    public Map m;
    public int collisionLayer;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    public boolean isAttacking = false;
    public ArrayList<Character> characters = new ArrayList<Character>();

    public enum CharacterEtat {
        STATIC, JUMPRUN, JUMP, JUMPWALK, WALK, RUN, FALL, FALLWALK, FALLRUN, DEAD;
    }

    public enum CharacterFacing {
        LEFT, RIGHT
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
        Anim Animation, 
        double jumpHeight,
        int hitboxWidth,
        int hitboxHeight
    ){
        this.m = m;
        this.collisionLayer = collisionLayer;
        this.hp = hp;
        this.maxHp = hp;
        this.defence = defence;
        this.strength = strength;
        this.agility = agility;
        this.additionalStrength = 0;
        this.additionaldefence = 0;
        this.additionalAgility = 0;
        this.nom = nom;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.hitbox = new Rectangle((int)(xPosition+(0.5*(hitboxWidth-width))), (int) (yPosition+(0.5*(hitboxHeight-height))), (int) hitboxWidth, (int) hitboxHeight);
        this.etat = CharacterEtat.STATIC;
        this.facing = CharacterFacing.LEFT;
        this.Animation = Animation;
        this.jumpHeight = jumpHeight;
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
        selectAnimation();
        batch.draw(Animation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);
        // renderHitbox(hitbox);
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

    public void move() {
        if(isAttacking){
            attaquer(characters);
            isAttacking = false;
        }
        double movespeed = 0;
        if(this.etat == CharacterEtat.WALK || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.FALLWALK ){
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
            decrementY();
        }
    }

    public void decrementY(){
        if(!detectCollision(xPosition, yPosition-4, width, height)){
            this.etat = CharacterEtat.FALL;
            hitbox.y -= 4;
            this.yPosition -= 4;
        } else {
            this.etat = CharacterEtat.STATIC;

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
            decrementY();
        } else {
            this.etat = CharacterEtat.STATIC;
        }
    }
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

    public void setAdditionalStrength(int additionalStrength){
        this.additionalStrength = additionalStrength;
    }

    public int getAdditionalStrength(){
        return this.additionalStrength;
    }
    
    public void setAdditionaldefence(int additionaldefence){
        this.additionaldefence = additionaldefence;
    }

    public int getAdditionaldefence(){
        return this.additionaldefence;
    }
    
    public void setAdditionalAgility(int additionalAgility){
        this.additionalAgility = additionalAgility;
    }
    
    public int getAdditionalAgility(){
        return this.additionalAgility;
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

    public void renderHitbox(Rectangle h){
        shapeRenderer.begin(ShapeType.Filled) ;
        shapeRenderer.rect(h.x, h.y, h.width, h.height) ;
        shapeRenderer.end();
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
        return new Rectangle((int)(xPosition - (width/2)), (int)(yPosition + (height/2)), (int)(2*width), (int)(0.5*height));
    }

    public void isAttacking(){
        isAttacking = true;
    }

    public void setCharacters(ArrayList<Character> c){
        characters = c;
    }

    public void attaquer(ArrayList<Character> characters){
        Rectangle attackHitbox = getAttackHitbox();
        
        for(Character c : characters){
            if(c != this){
                if(attackHitbox.overlaps(c.getHitbox())){
                    c.getDamaged(this.strength);
                }
            }
        }
    }

    public void getDamaged(int damageHp){
        this.hp -= (int)((1.0 - ((float) this.defence/100.0)) * (float)damageHp);
    }
}
