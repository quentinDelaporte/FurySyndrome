package com.delaporte.furysyndrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.delaporte.furysyndrom.Character.Character;

public class KeyEvent {

    public KeyEvent(){
    }

    public void keyPressed(Character j1){
        System.out.println(j1.etat);
		if(Gdx.input.isKeyPressed(Keys.ALT_RIGHT)){
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			    j1.setFacingToRight();
                if(j1.isJumping()) j1.setCharacterEtatJUMPRUN();
                else if(j1.isFalling())	j1.setCharacterEtatFALLRUN();
                else j1.setCharacterEtatRUN();
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
			    j1.setFacingToLeft();
                if(j1.isJumping()) j1.setCharacterEtatJUMPRUN();
                else if(j1.isFalling())	j1.setCharacterEtatFALLRUN();
                else j1.setCharacterEtatRUN();
            } else if (Gdx.input.isKeyPressed(Keys.UP)) {
		        if(!j1.isJumping() && !j1.isFalling()) j1.setJumping();
		    } else {
                j1.setCharacterEtatSTATIC();
            }
        } else if (Gdx.input.isKeyPressed(Keys.UP)) {

            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            } else {

            }
		    if(!j1.isJumping() && !j1.isFalling()) j1.setJumping();

		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			j1.setFacingToRight();
            if(j1.isJumping()) j1.setCharacterEtatJUMPRUN();
            else if(j1.isFalling()) j1.setCharacterEtatFALLRUN();
            else j1.setCharacterEtatWALK();
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)){
		    j1.setFacingToLeft();
            if(j1.isJumping()) j1.setCharacterEtatJUMPRUN();
            else if(j1.isFalling()) j1.setCharacterEtatFALLRUN();
            else j1.setCharacterEtatWALK();
        } else {
			if(!j1.isJumping() && !j1.isFalling()) j1.setCharacterEtatSTATIC();
        }
    }    	
}
