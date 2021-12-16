package com.delaporte.furysyndrom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.utils.ConfigReader;

//On creer notre propre gestionnaire d'event pour les touches car libgdx ne gere pas nativement les associations de touches
// Sauf Ctrl, alt, shift cf: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/scenes/scene2d/utils/UIUtils.java
    /**
     * Keys par défaut:
     *      J1:
     *          NUMPAD_4 : LEFT
     *          NUMPAD_6 : RIGHT
     *          NUMPAD_0 : RUN
     *          NUMPAD_8 : UP
     *      j:
     *          Q : LEFT
     *          D : RIGHT
     *          CONTROL_LEFT : RUN
     *          Z : UP
     */

public class KeyEvent {
    private Character j1; 
    private Character j2; 
    private ConfigReader ConfigReader = new ConfigReader();

    public KeyEvent(){
    }

    public void keyPressed(Character j1, Character j2){
        this.j1 = j1;
        this.j2 = j2;
            
        DetecterAction(j1,1);
        DetecterAction(j2,2);

    }

    public void DetecterAction(Character j, int numChar){
        if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player"+numChar+"_left_key")))) j.setFacingToLeft();
        else if (Gdx.input.isKeyPressed(Integer.parseInt(ConfigReader.getKeyProperties("player"+numChar+"_right_key")))) j.setFacingToRight();
        
        if(isRunning(numChar) && !isWalking(numChar) && !isFalling(j) && !isJumping(j,numChar))
            j.setCharacterEtatRUN();
        if(!isRunning(numChar) && isWalking(numChar) && !isFalling(j) && !isJumping(j,numChar))
            j.setCharacterEtatWALK();
        if(isJumping(j,numChar) && !isFalling(j) && !isWalking(numChar) && !isRunning(numChar) && canJump(j,numChar))
            j.setCharacterEtatJUMP();
        if(isJumping(j,numChar) && isWalking(numChar) && !isRunning(numChar))
            j.setCharacterEtatJUMPWALK();
        if(isJumping(j,numChar) && !isWalking(numChar) && isRunning(numChar))
            j.setCharacterEtatJUMPRUN();
        if(isFalling(j) && !isWalking(numChar) && !isRunning(numChar))
            j.setCharacterEtatFALLSTATIC();
        if(isFalling(j) && !isWalking(numChar) && isRunning(numChar))
            j.setCharacterEtatFALLRUN();
        if(isFalling(j) && isWalking(numChar) && !isRunning(numChar))
            j.setCharacterEtatFALLWALK();
        if(!isFalling(j) && !isJumping(j,numChar) && !isRunning(numChar) && !isWalking(numChar))
            j.setCharacterEtatSTATIC();
        if(isAttacking(numChar)){
            j.isAttacking();
        }
    }

    public boolean isPauseKeyPressed(){
        return Gdx.input.isKeyPressed(Keys.ESCAPE);
    }

    public boolean isAnyKeyPressed(){
        return Gdx.input.isKeyPressed(Keys.ANY_KEY);
    }

    public boolean canJump(Character j, int playerNumber){
        if( 
            !j.isFalling() && 
            !j.isJumping() &&
            Gdx.input.isKeyPressed(
                Integer.parseInt(
                    ConfigReader.getKeyProperties("player"+playerNumber+"_jump_key")
                )
            )
        )
            return true;
        return false;
    }

    public boolean isJumping(Character j, int playerNumber) {
        if(
            (
                !j.isFalling() && 
                j.isJumping()
            ) || 
            (
                !j.isFalling() && 
                !j.isJumping() && 
                Gdx.input.isKeyPressed(
                    Integer.parseInt(
                        ConfigReader.getKeyProperties("player"+playerNumber+"_jump_key")
                    )
                )
            )
        )
            return true;
        return false;
    }

    public boolean isFalling(Character j) {
        if(j.isFalling())
            return true;
        return false;
    }

    public boolean isRunning(int playerNumber){
        if(
            Gdx.input.isKeyPressed(
                Integer.parseInt(
                    ConfigReader.getKeyProperties("player"+playerNumber+"_run_key")
                )
            ) && 
            (   
                Gdx.input.isKeyPressed(
                    Integer.parseInt(
                        ConfigReader.getKeyProperties("player"+playerNumber+"_right_key")
                    )
                ) ||
                Gdx.input.isKeyPressed(
                    Integer.parseInt(
                        ConfigReader.getKeyProperties("player"+playerNumber+"_left_key")
                    )
                )
            )
        )
            return true;
        return false;
    }

    public boolean isWalking(int playerNumber){
        if( 
            !Gdx.input.isKeyPressed(
                Integer.parseInt(
                    ConfigReader.getKeyProperties("player"+playerNumber+"_run_key")
                )
            ) && 
            (
                Gdx.input.isKeyPressed(
                    Integer.parseInt(
                        ConfigReader.getKeyProperties("player"+playerNumber+"_right_key")
                    )
                ) ||
                Gdx.input.isKeyPressed(
                    Integer.parseInt(
                        ConfigReader.getKeyProperties("player"+playerNumber+"_left_key")
                    )
                )
            )
        )
            return true;
        return false;
    }

    public boolean isAttacking(int playerNumber){
        if( 
            Gdx.input.isKeyPressed(
                Integer.parseInt(
                    ConfigReader.getKeyProperties("player"+playerNumber+"_attack_one_key")
                )
            ) ||
            Gdx.input.isKeyPressed(
                Integer.parseInt(
                    ConfigReader.getKeyProperties("player"+playerNumber+"_attack_two_key")
                )
            )
        )
            return true;
        return false;
    }

    public boolean isKeyPressed(int key){
        if(Gdx.input.isKeyPressed(key))
            return true;
        return false;
    }
        
    /** 
     * Il y a 194 caracteres différents géré par libgdx
     * cf : https://libgdx.badlogicgames.com/ci/nightlies/docs/api/constant-values.html
    **/
    public int getKeyCodePressed(){
        for(int i = 1; i<194; i++)
            if(Gdx.input.isKeyPressed(i))
                return i;
        return 0;
    }

    public void reloadConfig(){
        this.ConfigReader.reload();
    }
}
