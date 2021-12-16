package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.utils.ConfigReader;
import com.badlogic.gdx.Input;
import com.delaporte.furysyndrom.Sound.AmbiantSound;

public class HotkeyButton {
  private Skin skin = new Skin(Gdx.files.internal("../assets/Skin/comic-ui.json"));
  private TextButton button;
  private String paramName;
  private String keyValue;
  private boolean isActive = false;
  private ConfigReader configReader = new ConfigReader();
  private boolean isEnabled = true;
  public int x;
  public int y;
  public int width;
  public int height;
  public AmbiantSound sound = new AmbiantSound(Float.parseFloat(configReader.getGeneralProperties("Sound_Volume")), "../assets/Sound/FX/UI/menuNavigation.wav");
  FurySyndrom game;

  public HotkeyButton(int x, int y, int width, int height, FurySyndrom game, String paramName){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.game = game;
    isActive = false;
    this.paramName = paramName;
    this.keyValue = Input.Keys.toString(Integer.parseInt(configReader.getKeyProperties(paramName)));
    generer();
  }

  public void generer(){
    button = new TextButton(keyValue+"", skin);
    button.setSize(width, height);
    button.setPosition(x,y);
    button.setText(keyValue);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        isActive = true;
        if(sound != null)
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

  public void setText(){
    configReader.reload();
    button.setText(Input.Keys.toString(Integer.parseInt(configReader.getKeyProperties(this.paramName))));
  }

  public void updateVolume() {
    sound.setVolume(Float.parseFloat(configReader.getGeneralProperties("Sound_Volume")));
  }
  
}