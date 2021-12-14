package com.delaporte.furysyndrom;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.delaporte.furysyndrom.Screen.GameScreen;
import com.delaporte.furysyndrom.Screen.PlayerNumberSelectorScreen;
import com.delaporte.furysyndrom.Screen.TitleScreen;

public class FurySyndrom extends Game {

    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
	public int w = 1920;
	public int h = 800;
	public OrthographicCamera camera;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public void drawCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.position.x = w/2;
		camera.position.y = h/2;
		camera.update();
	}
}