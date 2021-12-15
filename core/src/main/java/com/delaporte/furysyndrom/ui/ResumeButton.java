package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Screen.GameScreen;
import com.delaporte.furysyndrom.Sound.AmbiantSound;

public class ResumeButton extends CustomButton {
  private ImageButton button;
  private boolean isPaused = true;

  public ResumeButton(String text, int x, int y, int width, int height, FurySyndrom game) {
    super(
      text,
      x,
      y,
      width,
      height,
      game
    );
  }

  @Override
  public void generer(){
    TextureRegionDrawable imageup = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../assets/Texture/UI/BUTTONS/ResumeButton.png"))));
    TextureRegionDrawable imagedown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../assets/Texture/UI/BUTTONS/ResumeButton.png"))));
    button = new ImageButton(imageup, imagedown);
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        isPaused=false;
        sound.play();
      }
      @Override
      public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
      }
    });
  }

  public ImageButton getButton(){
    return button;
  }

  public boolean IsPaused(){
    return isPaused;
  }

  public void reset(){
    isPaused=true;
  }
}