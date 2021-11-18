package com.delaporte.furysyndrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class FurySyndrom extends ApplicationAdapter {
private SpriteBatch batch;
	private Character mario;
	private int layerToRender[] = { 0, 1 };
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;
	private Map map01;
	private float stateTime;
	private MapObjects collisionObjects;
	private int minCameraY = 0;
	private double jumpHeight;
	private boolean deadSoundPlayed = false;
	private Texture backgroundImg;
	private boolean gameStarted = false;
	private float volume = 1f;
	private Parameter parameter;

	@Override
	public void create() {
		batch = new SpriteBatch();
		drawCamera();
		map01 = new Map("../assets/Map/map.tmx");
		collisionObjects = map01.getCollisionTile(2);
		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();

	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(layerToRender);
		batch.begin();

		if (gameStarted) {
		} else {
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
				gameStarted = true;
			}
		}
		camera.update();
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	private void sleep(double second) throws InterruptedException {
		Thread.sleep((int) (second * 1000));
	}

	private void drawCamera() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
	}


	private void keyPressed() {

	}
}
