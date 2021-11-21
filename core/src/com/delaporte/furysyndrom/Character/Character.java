package com.delaporte.furysyndrom.Character;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.delaporte.furysyndrom.Anim;
import com.delaporte.furysyndrom.Map;

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
    public Map m;
    public int collisionLayer;

    public enum CharacterEtat {
        STATIC, JUMPRUN, JUMP, JUMPWALK, WALK, RUN, FALL, FALLWALK, FALLRUN, DEAD;
    }

    public enum CharacterFacing {
        LEFT, RIGHT
    }
    
    protected Character(
        Map m, 
        int collisionLayer,
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
        this.m = m;
        this.collisionLayer = collisionLayer;
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
        this.hitbox = new Rectangle((int) (xPosition+0.25*width), (int)( yPosition+0.25*height), (int) (0.5*this.width), (int) (0.5*this.height));
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
        batch.draw(staticCharacterAnimation.getAnimation(stateTime), (float) xPosition, (float) yPosition, (float) width, (float) height);
        hitbox = new Rectangle((int) xPosition, (int) yPosition, (int) this.width, (int) this.height);
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
            this.etat = CharacterEtat.JUMPWALK;
        }
    }

    public void move() {
        System.out.println(etat);
        double movespeed = 0;
        if(this.etat == CharacterEtat.WALK || this.etat == CharacterEtat.JUMPWALK || this.etat == CharacterEtat.FALLWALK ){
            movespeed = 1 + (this.agility/100);
        } else if(this.etat == CharacterEtat.RUN || this.etat == CharacterEtat.JUMPRUN || this.etat == CharacterEtat.FALLRUN ){
            movespeed = 2.4 + (this.agility/100);
        } else {
            movespeed = 0;
        }
        if(this.facing == CharacterFacing.RIGHT){
            if(!detectCollision((float)(xPosition+movespeed), yPosition, width, height)){
                this.xPosition += movespeed;
                hitbox.x += movespeed;
            }
        } else {
            if(!detectCollision((float)(xPosition-movespeed), yPosition, width, height)){
                this.xPosition -= movespeed;
                hitbox.x -= movespeed;
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

    public boolean detectCollision() {
        MapObjects collisionObjects = m.getCollisionTile(collisionLayer);
		for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, hitbox))
				return true;
		}
		for (PolygonMapObject polygonObject : collisionObjects.getByType(PolygonMapObject.class)) {
			Polygon polygon = polygonObject.getPolygon();
			Polygon hitboxPolygon = new Polygon(new float[] { hitbox.x, hitbox.y, hitbox.x + hitbox.width, hitbox.y,
					hitbox.x + hitbox.width, hitbox.y + hitbox.height, hitbox.x, hitbox.y + hitbox.height });
			if (Intersector.overlapConvexPolygons(polygon, hitboxPolygon))
                return true;
		}
        return false;
	}

    public boolean detectCollision(float x, float y, int w, int h) {
        Rectangle futurHitbox = new Rectangle(x, y, w, h);    
        MapObjects collisionObjects = m.getCollisionTile(collisionLayer);
		for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, futurHitbox))
				return true;
		}
		for (PolygonMapObject polygonObject : collisionObjects.getByType(PolygonMapObject.class)) {
			Polygon polygon = polygonObject.getPolygon();
			Polygon hitboxPolygon = new Polygon(new float[] { x, y, x + w, y, x + w, y + h, y, y + h });
			if (Intersector.overlapConvexPolygons(polygon, hitboxPolygon))
                return true;
		}
        return false;
	}

    public boolean detectCollision(Rectangle futurHitbox) {
        MapObjects collisionObjects = m.getCollisionTile(collisionLayer);
		for (RectangleMapObject rectangleObject : collisionObjects.getByType(RectangleMapObject.class)) {
			Rectangle rectangle = rectangleObject.getRectangle();
			if (Intersector.overlaps(rectangle, futurHitbox))
				return true;
		}
		for (PolygonMapObject polygonObject : collisionObjects.getByType(PolygonMapObject.class)) {
			Polygon polygon = polygonObject.getPolygon();
			Polygon hitboxPolygon = new Polygon(new float[] { futurHitbox.x, futurHitbox.y, futurHitbox.x + futurHitbox.width, futurHitbox.y,
					futurHitbox.x + futurHitbox.width, futurHitbox.y + futurHitbox.height, futurHitbox.x, futurHitbox.y + futurHitbox.height });
			if (Intersector.overlapConvexPolygons(polygon, hitboxPolygon))
                return true;
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

    public void setCharacterEtatJUMPRUN(){
    if(
            this.etat != CharacterEtat.JUMP &&
            this.etat != CharacterEtat.JUMPWALK &&
            this.etat != CharacterEtat.JUMPRUN
        ){
            this.etat = CharacterEtat.JUMPRUN;
            initialY = yPosition;
        } else if(this.etat == CharacterEtat.JUMP || this.etat == CharacterEtat.JUMPWALK) {
            this.etat = CharacterEtat.JUMPWALK;
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
}
