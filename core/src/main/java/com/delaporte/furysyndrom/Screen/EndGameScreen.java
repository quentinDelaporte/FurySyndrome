package com.delaporte.furysyndrom.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.Sound.BackgroundMusic;
import com.delaporte.furysyndrom.utils.ConfigReader;

public class EndGameScreen extends ScreenAdapter {

    FurySyndrom game;
    private Character winner;
	private BackgroundMusic gameMusic;
    private ConfigReader ConfigReader = new ConfigReader();

    public EndGameScreen(FurySyndrom game, Character c) {
        this.winner = c;
        this.game = game;
        game.drawCamera();
		gameMusic = new BackgroundMusic(Float.parseFloat(ConfigReader.getGeneralProperties("Music_Volume")), "../assets/Sound/Music/end-battle.mp3");
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    gameMusic.stop();
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
		Gdx.graphics.setWindowedMode(game.w, game.h);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("FurySyndrom");
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("../assets/EndGameScreen.png")), 0, 0, 640, 480);
        game.camera.update();
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}