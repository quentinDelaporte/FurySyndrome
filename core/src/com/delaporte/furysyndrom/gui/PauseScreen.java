package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.delaporte.furysyndrom.ui.ResumeButton;

public class PauseScreen extends CustomScreen {
   
	private ResumeButton ResumeButton;
	private Stage stage;

    public PauseScreen(int w, int h, Texture bckgrndImg) {
		super(w, h, bckgrndImg);
		generate();
    }

	@Override
	public void generate(){
		stage = new Stage(new ScreenViewport());
		ResumeButton = new ResumeButton("",(int)((w-250)/3)*1,(int)((h-250)/3)*1,250,250,4,stage);
		stage.addActor(ResumeButton.getButton());
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void draw(SpriteBatch batch, int width, int height) {
		batch.draw(backgroundImg, 0, 0, width, height);
	}

	public void drawScene(){
		stage.act();
		stage.draw();
	}

	public boolean isPaused(){
		return ResumeButton.IsPaused();
	}
}
