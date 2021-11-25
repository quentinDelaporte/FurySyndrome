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

public class SoundButton extends CustomButton {
  private ImageButton button;
  private boolean isSoundSettingsOpen = false;

  public SoundButton(String text, int x, int y, int width, int height, final int nbPlayerInput, FurySyndrom game){
    super(
      text,
      x,
      y,
      width,
      height,
      nbPlayerInput,
      game
    );
  }

  @Override
  public void generer(){
    TextureRegionDrawable imageup = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../../Assets/Texture/UI/BUTTONS/optionsButton.png"))));
    TextureRegionDrawable imagedown = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../../Assets/Texture/UI/BUTTONS/optionsButton.png"))));
    button = new ImageButton(imageup, imagedown);
    button.setSize(width, height);
    button.setPosition(x,y);
    button.addListener(new InputListener() {    
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        isSoundSettingsOpen=true;
        System.out.println("SOUND SETTINGS");
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

  public boolean isSoundSettingsOpen(){
    return isSoundSettingsOpen;
  }

  public void reset(){
    isSoundSettingsOpen = false;
  }
}