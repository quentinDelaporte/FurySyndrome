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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class CharacterHealImage extends Actor {
  private Image CharacterHealImage;
  private int x;
  private int y;
  private int width;
  private int height;
  private FurySyndrom game;
  private Texture texture;
  private Color color = Color.RED;
  private int healBarWidth = 99;


  public CharacterHealImage(String path, int x, int y, int width, int height, FurySyndrom game){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.game = game;
    this.CharacterHealImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../../Assets/Texture/UI/HEAL_STATE/PNG/" + path)))));
    this.CharacterHealImage.setSize(width, height);
    this.CharacterHealImage.setPosition(x,y);
    Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmap.setColor(color);
    pixmap.fillRectangle(0, 0, width, height);
    texture = new Texture(pixmap);
    pixmap.dispose();
  }

  public void draw(SpriteBatch batch, float parentAlpha){
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(texture, x+28, y+3, healBarWidth, 14);
    CharacterHealImage.draw(batch, parentAlpha);
  }

  public void setHealBarPercent(int hp, int maxHp){
    System.out.println("-----hpPercent bar-----");
    System.out.println(maxHp);
    System.out.println(hp);
    System.out.println(hp/maxHp);
    System.out.println(hp/maxHp*99);
    System.out.println("-----------------------");
    healBarWidth = (int)(((float)hp)/((float)maxHp) * 99)<0 ? 0 : (int)(((float)hp)/((float)maxHp) * 99);
  }
}