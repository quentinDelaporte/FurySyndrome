package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class MainScreen extends CustomScreen {
   

    public MainScreen(int w, int h, Texture bckgrndImg) {
		super(w, h, bckgrndImg);
    }

	@Override
	public void generate(){}

	@Override
	public void draw(SpriteBatch batch, int width, int height) {
		batch.draw(backgroundImg, 0, 0, width, height);
	}
}
