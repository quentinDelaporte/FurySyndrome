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

public class ButtonSelectorPlayer {
  private Stage stage;
  private Skin skin;
  private TextButton button;

  public ButtonSelectorPlayer(String text){
    skin = new Skin(Gdx.files.internal("../../Assets/Skin/freezing-ui.json"));
    stage = new Stage();
    button = new TextButton(text, skin);
    stage.addActor(button);
    Gdx.input.setInputProcessor(stage);
    
    button.setSize(50,100);
    button.setPosition(100,100);
    button.addListener(new InputListener() {    
      @Override
      public void enter(InputEvent event, float x, float y, int pointer, @Null Actor fromActor){
        System.out.println("enter started at (" + x + ", " + y + ")");
      }
      
      @Override
      public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("touchUp started at (" + x + ", " + y + ")");
      }
      @Override
      public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        System.out.println("touchDown started at (" + x + ", " + y + ")");
        return true;
      }
    });
  }

  public void draw() {
      stage.act();
      stage.draw();
      
  }
}