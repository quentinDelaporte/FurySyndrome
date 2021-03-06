package com.delaporte.furysyndrom.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.delaporte.furysyndrom.FurySyndrom;

public class PlayerNumberSelectorScreen extends ScreenAdapter {

    FurySyndrom game;
	private ButtonSelectorPlayer ButtonSelector4Player;
	private ButtonSelectorPlayer ButtonSelector2Player;
	private Stage stage;
    private Texture textureBg = new Texture(Gdx.files.internal("../assets/MainImage.png"));

    public PlayerNumberSelectorScreen(FurySyndrom game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
		ButtonSelector2Player = new ButtonSelectorPlayer("2",((game.w-250)/3)*1,((game.h-100)/3)*1,250,100,4, this.game);
		ButtonSelector4Player = new ButtonSelectorPlayer("4",((game.w-250)/3)*2,((game.h-100)/3)*1,250,100,4, this.game);
		stage.addActor(ButtonSelector4Player.getButton());
		stage.addActor(ButtonSelector2Player.getButton());
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
		Gdx.graphics.setWindowedMode(game.w, game.h);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        
        game.batch.draw(textureBg, 0, 0, 640, 480);
        game.batch.end();	
        stage.act();
        stage.draw();
        if(getNbPlayer() != 0){
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public int getNbPlayer(){
		return (
			ButtonSelector2Player.getNbPlayer() == 0) 
			? ButtonSelector4Player.getNbPlayer() : 
			(ButtonSelector2Player.getNbPlayer());
	}
}