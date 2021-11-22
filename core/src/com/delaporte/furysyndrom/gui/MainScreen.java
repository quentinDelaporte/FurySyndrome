package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class MainScreen {
   
    private Texture backgroundImg;
    public MainScreen() {
		backgroundImg = new Texture(Gdx.files.internal("../../Assets/MainImage.png"));
    }

	public void draw(SpriteBatch batch, int width, int height) {
			batch.draw(backgroundImg, 0, 0, width, height);
	}
}
