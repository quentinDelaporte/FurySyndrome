package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class CharacterHealImage extends Actor {
  private Image CharacterHealImage;
  private int x;
  private int y;
  private Texture texture;
  private Color color = Color.RED;
  private int healBarWidth = 99;


  public CharacterHealImage(String path, int x, int y, int width, int height){
    this.x = x;
    this.y = y;
    this.CharacterHealImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("../assets/Texture/UI/HEAL_STATE/PNG/" + path)))));
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
    healBarWidth = (int)(((float)hp)/((float)maxHp) * 99)<0 ? 0 : (int)(((float)hp)/((float)maxHp) * 99);
  }
}