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
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.*;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Screen.GameScreen;
import com.delaporte.furysyndrom.utils.ConfigReader;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

public class HotkeyButton extends CustomButton {
  private Skin skin;
  private TextButton button;
  private boolean nextGameState = false;
  private String param_name;
  private String key_value;
  private boolean isActive = false;
  private ConfigReader ConfigReader = new ConfigReader();
  private boolean isEnabled = true;

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
    this.key_value = Input.Keys.toString(Integer.parseInt(ConfigReader.getKeyProperties(param_name)));
    System.out.println(key_value);
  }

  @Override
  public void generer(){
    skin = new Skin(Gdx.files.internal("../../Assets/Skin/freezing-ui.json"));
    button = new TextButton(key_value, skin);
    //Text: mapper les keysCode aux touches 
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        isActive = true;
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

  public boolean isActive(){
    return isActive;
  }
  
  public void reset(){
    this.isActive = false;
  }

  public void changeEnabled(boolean enabled){
    this.isEnabled = enabled;
  }

  public boolean isEnabled(){
    return isEnabled;
  }

  public void setText(String param_name){
    button.setText(Input.Keys.toString(Integer.parseInt(ConfigReader.getKeyProperties(param_name))));
  }
}