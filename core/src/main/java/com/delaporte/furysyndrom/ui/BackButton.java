package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.FurySyndrom;

public class BackButton extends CustomButton {
  private ImageButton button;
  private boolean goBack = false;

  public BackButton(String text, int x, int y, int width, int height, FurySyndrom game){
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
    TextureRegionDrawable imageup = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../assets/Texture/UI/BUTTONS/BackButton.png"))));
    TextureRegionDrawable imagedown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../assets/Texture/UI/BUTTONS/BackButton.png"))));
    button = new ImageButton(imageup, imagedown);
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        goBack=true;
        if(sound != null)
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

  public boolean goBack(){
    return goBack;
  }

  public void reset(){
    goBack=false;
  }
}