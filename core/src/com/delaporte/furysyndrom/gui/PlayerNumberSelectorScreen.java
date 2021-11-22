package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PlayerNumberSelectorScreen {
   
    private Texture backgroundImg;
    public PlayerNumberSelectorScreen() {
		backgroundImg = new Texture(Gdx.files.internal("../../Assets/HowManyPlayer.png"));
    }

	public void draw(SpriteBatch batch, int width, int height, Stage stage) {
		batch.draw(backgroundImg, 0, 0, width, height);
		stage.act();
        stage.draw();   
	}
}
