package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Screen.GameScreen;
import com.delaporte.furysyndrom.Sound.AmbiantSound;

public class ButtonSelectorPlayer extends CustomButton {
  private Skin skin;
  private TextButton button;
  private boolean nextGameState = false;
  private int nbPlayer=0;
  private int nbPlayerBtn;
  public ButtonSelectorPlayer(String text, int x, int y, int width, int height, final int nbPlayerInput, FurySyndrom game) {
    super(
      text,
      x,
      y,
      width,
      height,
      game
    );
    nbPlayerBtn = nbPlayerInput;
  }

  @Override
  public void generer(){
    skin = new Skin(Gdx.files.internal("../assets/Skin/comic-ui.json"));
    button = new TextButton(text, skin);
    
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        nextGameState = true;
        nbPlayer=nbPlayerBtn;
        if(getNbPlayer() != 0){
          game.setScreen(new GameScreen(game));
        }
        sound.play();
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

  public boolean getNextGameState(){
    return nextGameState;
  }

  public int getNbPlayer(){
    return nbPlayer;
  }
}