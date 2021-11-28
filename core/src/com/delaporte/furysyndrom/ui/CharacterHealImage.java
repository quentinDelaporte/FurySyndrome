package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Screen.GameScreen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CharacterHealImage extends Actor {
  private Image CharacterHealImage;
  private int x;
  private int y;
  private int width;
  private int height;
  private FurySyndrom game;

  public CharacterHealImage(String path, int x, int y, int width, int height, FurySyndrom game){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.game = game;
    this.CharacterHealImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../../Assets/Texture/UI/HEAL_STATE/PNG/" + path)))));
    this.CharacterHealImage.setSize(width, height);
    this.CharacterHealImage.setPosition(x,y);
  }

  public void draw(SpriteBatch batch, float parentAlpha){
    CharacterHealImage.draw(batch, parentAlpha);
  }

  public void setImage(String image){
    CharacterHealImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../../Assets/Texture/UI/HEAL_STATE/PNG/"+image)))));
  }
}