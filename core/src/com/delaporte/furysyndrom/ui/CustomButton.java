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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.delaporte.furysyndrom.FurySyndrom;

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

  protected abstract void generer();

  public Button getButton(){
    return button;
  }

  public boolean getNextGameState(){
    return nextGameState;
  }
}