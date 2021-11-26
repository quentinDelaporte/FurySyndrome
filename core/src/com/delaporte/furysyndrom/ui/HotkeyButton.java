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
import com.delaporte.furysyndrom.utils.ConfigReader;

public class HotkeyButton extends CustomButton {
  private Skin skin;
  private TextButton button;
  private boolean nextGameState = false;
  private String param_name;
  private String key_value;
  private boolean isActive = false;
  private ConfigReader ConfigReader = new ConfigReader();

  public HotkeyButton(String text, int x, int y, int width, int height, FurySyndrom game, String param_name){
    super(
      text,
      x,
      y,
      width,
      height,
      game
    );
    isActive = false;
    this.param_name = param_name;
    this.key_value = ConfigReader.getKeyProperties(param_name);
  }

  @Override
  public void generer(){
    skin = new Skin(Gdx.files.internal("../../Assets/Skin/freezing-ui.json"));
    button = new TextButton("1", skin);
    //Text: mapper les keysCode aux touches 
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        ConfigReader.updateKeyProperties(param_name, key_value);
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
}