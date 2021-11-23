package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;

public class ButtonSelectorPlayer extends CustomButton {
  private Skin skin;
  private TextButton button;
  private boolean nextGameState = false;
  private int nbPlayer=0;


  public ButtonSelectorPlayer(String text, int x, int y, int width, int height, final int nbPlayerInput, Stage stage){
    super(
      text,
      x,
      y,
      width,
      height,
      nbPlayerInput,
      stage
    );
  }

  @Override
  public void generer(){
    skin = new Skin(Gdx.files.internal("../../Assets/Skin/freezing-ui.json"));
    button = new TextButton(text, skin);
    stage.addActor(button);
    
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        nextGameState = true;
        nbPlayer=nbPlayerInput;
      }
      @Override
      public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
      }
    });
  }

  public TextButton getButton(){
    return button;
  }

  public int getNbPlayer(){
    return nbPlayer;
  }
  
  public boolean getNextGameState(){
    return nextGameState;
  }
}