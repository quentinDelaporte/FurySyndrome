package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class PlayerNumberSelectorScreen {
   
    private Texture backgroundImg;
    public PlayerNumberSelectorScreen() {
		backgroundImg = new Texture(Gdx.files.internal("../../Assets/HowManyPlayer.png"));
    }

	public void draw(SpriteBatch batch, int width, int height) {
		batch.draw(backgroundImg, 0, 0, width, height);
	}
}
