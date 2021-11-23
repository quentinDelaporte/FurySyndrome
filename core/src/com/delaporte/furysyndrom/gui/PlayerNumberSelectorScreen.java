package com.delaporte.furysyndrom.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PlayerNumberSelectorScreen extends CustomScreen {
   
	private ButtonSelectorPlayer ButtonSelector4Player;
	private ButtonSelectorPlayer ButtonSelector2Player;
	private Stage stage;

    public PlayerNumberSelectorScreen(int w, int h, Texture bckgrndImg) {
		super(w, h, bckgrndImg);
		generate();
    }

	@Override
	public void generate(){
		stage = new Stage(new ScreenViewport());
		ButtonSelector2Player = new ButtonSelectorPlayer("2",(int)((w-250)/3)*1,(int)((h-250)/3)*1,250,250,4,stage);
		ButtonSelector4Player = new ButtonSelectorPlayer("4",(int)((w-250)/3)*2,(int)((h-250)/3)*1,250,250,2,stage);
		stage.addActor(ButtonSelector4Player.getButton());
		stage.addActor(ButtonSelector2Player.getButton());
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

	public int getNbPlayer(){
		return (
			ButtonSelector2Player.getNbPlayer() == 0) 
			? ButtonSelector4Player.getNbPlayer() : 
			(ButtonSelector2Player.getNbPlayer());
	}
}
