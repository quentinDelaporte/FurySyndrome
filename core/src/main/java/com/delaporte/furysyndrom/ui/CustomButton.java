package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Sound.AmbiantSound;
import com.delaporte.furysyndrom.utils.ConfigReader;

public abstract class CustomButton {
  public Skin skin;
  public TextButton button;
  public boolean nextGameState = false;
  public int nbPlayer=0;
  public String text;
  public int x;
  public int y;
  public int width;
  public int height;
  private ConfigReader configReader = new ConfigReader();
  public AmbiantSound sound = new AmbiantSound(Float.parseFloat(configReader.getGeneralProperties("Sound_Volume")), "../assets/Sound/FX/UI/menuNavigation.wav");
  FurySyndrom game;

  protected CustomButton(String text, int x, int y, int width, int height, FurySyndrom game){
    this.text = text;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.game = game;
    generer();
  }

  public void updateVolume() {
    sound.setVolume(Float.parseFloat(configReader.getGeneralProperties("Sound_Volume")));
  }

  protected abstract void generer();

  public Button getButton(){
    return button;
  }

  public boolean getNextGameState(){
    return nextGameState;
  }
}