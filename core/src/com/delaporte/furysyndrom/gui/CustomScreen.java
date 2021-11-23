package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class CustomScreen {
   
    public Texture backgroundImg;
	public int w;
	public int h;

    public CustomScreen(int w, int h, Texture backgroundImg) {
		this.w = w;
		this.h = h;
		this.backgroundImg = backgroundImg;
	}

    protected abstract void generate();


	protected abstract void draw(SpriteBatch batch, int width, int height);
}
