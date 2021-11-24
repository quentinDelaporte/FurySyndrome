package com.delaporte.furysyndrom.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.delaporte.furysyndrom.Screen.GameScreen;
import com.delaporte.furysyndrom.Screen.PlayerNumberSelectorScreen;
import com.delaporte.furysyndrom.FurySyndrom;

public class TitleScreen extends ScreenAdapter {

    FurySyndrom game;

    public TitleScreen(FurySyndrom game) {
        this.game = game;
        game.drawCamera();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                System.out.println(keyCode);
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new PlayerNumberSelectorScreen(game));
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
		Gdx.graphics.setTitle("FurySyndrom | FPS:" + Gdx.graphics.getFramesPerSecond());
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("../../Assets/MainImage.png")), 0, 0, 640, 480);
        game.camera.update();
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }


}