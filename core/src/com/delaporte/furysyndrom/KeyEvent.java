package com.delaporte.furysyndrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.utils.ConfigReader;
//On creer notre propre gestionnaire d'event pour les touches car libgdx ne gere pas nativement les associations de touches
// Sauf Ctrl, alt, shift cf: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/scenes/scene2d/utils/UIUtils.java
public class KeyEvent {
    private Character j1; 
    private Character j2; 
    private ConfigReader ConfigReader = new ConfigReader();
    public KeyEvent(){
    }

    /**
     * Keys par défaut:
     *      J1:
     *          NUMPAD_4 : LEFT
     *          NUMPAD_6 : RIGHT
     *          NUMPAD_0 : RUN
     *          NUMPAD_8 : UP
     *      J2:
     *          Q : LEFT
     *          D : RIGHT
     *          CONTROL_LEFT : RUN
     *          Z : UP
     */


    public void keyPressed(Character j1, Character j2){
        this.j1 = j1;
        this.j2 = j2;
        if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_left_key")))) j1.setFacingToLeft();
        else if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_right_key")))) j1.setFacingToRight();
        if(isRunning(1) && !isWalking(1) && !isFalling(j1) && !isJumping(j1,1))
            j1.setCharacterEtatRUN();
        if(!isRunning(1) && isWalking(1) && !isFalling(j1) && !isJumping(j1,1))
            j1.setCharacterEtatWALK();
        if(isJumping(j1,1) && !isFalling(j1) && !isWalking(1) && !isRunning(1) && canJump(j1,1))
            j1.setCharacterEtatJUMP();
        if(isJumping(j1,1) && isWalking(1) && !isRunning(1))
            j1.setCharacterEtatJUMPWALK();
        if(isJumping(j1,1) && !isWalking(1) && isRunning(1))
            j1.setCharacterEtatJUMPRUN();
        if(isFalling(j1) && !isWalking(1) && !isRunning(1))
            j1.setCharacterEtatFALLSTATIC();
        if(isFalling(j1) && !isWalking(1) && isRunning(1))
            j1.setCharacterEtatFALLRUN();
        if(isFalling(j1) && isWalking(1) && !isRunning(1))
            j1.setCharacterEtatFALLWALK();
        if(!isFalling(j1) && !isRunning(1) && !isJumping(j1,1) && !isWalking(1))
            j1.setCharacterEtatSTATIC();
            
        if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_left_key")))) j2.setFacingToLeft();
        else if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_right_key")))) j2.setFacingToRight();
        if(isRunning(2) && !isWalking(2) && !isFalling(j2) && !isJumping(j2,2))
            j2.setCharacterEtatRUN();
        if(!isRunning(2) && isWalking(2) && !isFalling(j2) && !isJumping(j2,2))
            j2.setCharacterEtatWALK();
        if(isJumping(j2,2) && !isFalling(j2) && !isWalking(2) && !isRunning(2) && canJump(j2,2))
            j2.setCharacterEtatJUMP();
        if(isJumping(j2,2) && isWalking(2) && !isRunning(2))
            j2.setCharacterEtatJUMPWALK();
        if(isJumping(j2,2) && !isWalking(2) && isRunning(2))
            j2.setCharacterEtatJUMPRUN();
        if(isFalling(j2) && !isWalking(2) && !isRunning(2))
            j2.setCharacterEtatFALLSTATIC();
        if(isFalling(j2) && !isWalking(2) && isRunning(2))
            j2.setCharacterEtatFALLRUN();
        if(isFalling(j2) && isWalking(2) && !isRunning(2))
            j2.setCharacterEtatFALLWALK();
        if(!isFalling(j2) && !isRunning(2) && !isJumping(j2,2) && !isWalking(2))
            j2.setCharacterEtatSTATIC();
    }

    public boolean isPauseKeyPressed(){
        return Gdx.input.isKeyPressed(Keys.ESCAPE);
    }

    public boolean isAnyKeyPressed(){
        return Gdx.input.isKeyPressed(Keys.ANY_KEY);
    }

    public boolean canJump(Character j, int playerNumber){
        if(playerNumber == 1){
            if( (
                !j.isFalling() && 
                !j.isJumping() &&
                Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_jump_key")))
                )
            )
                return true;
        } else if(playerNumber == 2){
            if( (
                !j.isFalling() && 
                !j.isJumping() &&
                Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_jump_key")))
                )
            )
                return true;
        }
        return false;
    }

    public boolean isJumping(Character j, int playerNumber) {
        if(playerNumber == 1){
            if(
                (
                    !j.isFalling() && 
                    j.isJumping()
                ) || 
                (
                    !j.isFalling() && 
                    !j.isJumping() && 
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_jump_key")))
                )
            )
                return true;
        } else if(playerNumber == 2){
            if(
                (
                    !j.isFalling() && 
                    j.isJumping()
                ) || 
                (
                    !j.isFalling() && 
                    !j.isJumping() && 
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_jump_key")))
                )
            )
                return true;
        }
        return false;
    }

    public boolean isFalling(Character j) {
        if(j.isFalling())
            return true;
        return false;
    }

    public boolean isRunning(int playerNumber){
        if(playerNumber == 1){
            if( Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_run_key"))) && 
                (   Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_right_key"))) ||
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_left_key")))
                )
            )
                return true;
        } else if(playerNumber == 2){
            if( Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_run_key"))) && 
                (   Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_right_key"))) ||
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_left_key")))
                )
            )
                return true;
        }
        return false;
    }

    public boolean isWalking(int playerNumber){
        if(playerNumber == 1){
            if( !Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_run_key"))) && 
                (   Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_right_key"))) ||
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player1_left_key")))
                )
            )
                return true;
        } else if(playerNumber == 2){
            if( !Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_run_key"))) && 
                (   Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_right_key"))) ||
                    Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player2_left_key")))
                )
            )
                return true;
        }
        return false;
    }
}
