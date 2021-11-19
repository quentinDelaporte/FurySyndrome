package com.delaporte.furysyndrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.delaporte.furysyndrom.Character.Character;

public class KeyEvent {
    private Character j; 
    public KeyEvent(){
    }

    public void keyPressed(Character j){
        this.j = j;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) j.setFacingToLeft();
        else if (Gdx.input.isKeyPressed(Keys.RIGHT)) j.setFacingToRight();
        
        if(isRunning() && !isWalking() && !isFalling() && !isJumping()){
            j.setCharacterEtatRUN();
        }
        if(!isRunning() && isWalking() && !isFalling() && !isJumping()){
            j.setCharacterEtatWALK();
        }
        if(isJumping() && !isFalling() && !isWalking() && !isRunning() && canJump()){
            j.setCharacterEtatJUMP();
        }
        if(isJumping() && isWalking() && !isRunning()){
            j.setCharacterEtatJUMPWALK();
        }
        if(isJumping() && !isWalking() && isRunning()){
            j.setCharacterEtatJUMPRUN();
        }
        if(isFalling() && !isWalking() && !isRunning()){
            j.setCharacterEtatFALLSTATIC();
        }
        if(isFalling() && !isWalking() && isRunning()){
            j.setCharacterEtatFALLRUN();
        }
        if(isFalling() && isWalking() && !isRunning()){
            j.setCharacterEtatFALLWALK();
        }
        if(!isFalling() && !isRunning() && !isJumping() && !isWalking()){
            j.setCharacterEtatSTATIC();
        }
    }

    public boolean canJump(){
        if( (
            !j.isFalling() && 
            !j.isJumping() &&
            Gdx.input.isKeyPressed(Keys.UP)
            )
        )
            return true;
        return false;
    }

    public boolean isJumping() {
        if(
            (
                !j.isFalling() && 
                j.isJumping()
            ) || 
            (
                !j.isFalling() && 
                !j.isJumping() && 
                Gdx.input.isKeyPressed(Keys.UP)
            )
        )
            return true;
        return false;
    }

    public boolean isFalling() {
        if(j.isFalling())
            return true;
        return false;
    }

    public boolean isRunning(){
        if( Gdx.input.isKeyPressed(Keys.ALT_LEFT) && 
            (   Gdx.input.isKeyPressed(Keys.LEFT) ||
                Gdx.input.isKeyPressed(Keys.RIGHT)
            )
        )
            return true;
        return false;
    }

    public boolean isWalking(){
        if( !Gdx.input.isKeyPressed(Keys.ALT_LEFT) && 
            (   Gdx.input.isKeyPressed(Keys.LEFT) ||
                Gdx.input.isKeyPressed(Keys.RIGHT)
            )
        )
            return true;
        return false;
    }

    // public void keyPressed(Character j){
    //     System.out.println(j.etat);
	// 	if(Gdx.input.isKeyPressed(Keys.ALT_LEFT)){
    //         if (Gdx.input.isKeyPressed(Keys.LEFT)) {
	// 		    j.setFacingToRight();
    //             if(j.isJumping()) j.setCharacterEtatJUMPRUN();
    //             else if(j.isFalling())	j.setCharacterEtatFALLRUN();
    //             else j.setCharacterEtatRUN();
    //         } else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
	// 		    j.setFacingToLeft();
    //             if(j.isJumping()) j.setCharacterEtatJUMPRUN();
    //             else if(j.isFalling())	j.setCharacterEtatFALLRUN();
    //             else j.setCharacterEtatRUN();
    //         } else if (Gdx.input.isKeyPressed(Keys.UP)) {
	// 	        if(!j.isJumping() && !j.isFalling()) j.setCharacterEtatJUMP();
	// 	    } else {
    //             j.setCharacterEtatSTATIC();
    //         }
    //     } else if (Gdx.input.isKeyPressed(Keys.UP)) {

    //         if (Gdx.input.isKeyPressed(Keys.LEFT)) {
    //         } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
    //         } else {

    //         }
	// 	    if(!j.isJumping() && !j.isFalling()) j.setCharacterEtatJUMP();

	// 	} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
	// 		j.setFacingToRight();
    //         if(j.isJumping()) j.setCharacterEtatJUMPRUN();
    //         else if(j.isFalling()) j.setCharacterEtatFALLRUN();
    //         else j.setCharacterEtatWALK();
    //     } else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
	// 	    j.setFacingToLeft();
    //         if(j.isJumping()) j.setCharacterEtatJUMPRUN();
    //         else if(j.isFalling()) j.setCharacterEtatFALLRUN();
    //         else j.setCharacterEtatWALK();
    //     } else {
	// 		if(!j.isJumping() && !j.isFalling()) j.setCharacterEtatSTATIC();
    //     }
    // }    	
}
