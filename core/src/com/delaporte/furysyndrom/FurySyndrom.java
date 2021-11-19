package com.delaporte.furysyndrom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;

import com.delaporte.furysyndrom.Character.Mage;
import com.delaporte.furysyndrom.Sound.BackgroundMusic;

public class FurySyndrom extends ApplicationAdapter {
	private SpriteBatch batch;
	private int layerToRender[] = { 0, 1, 2, 3, 4, 5, 6};
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;
	private Map map01;
	private float stateTime;
	private MapObjects collisionObjects;
	private Parameter parameter;
	private Mage j1;
	private int windowWidth = 2000;
	private int windowHeight = 800;
	private KeyEvent KeyEvent = new KeyEvent();
	private BackgroundMusic musicMenu;
	private float volume = 1f;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
		batch = new SpriteBatch();
		drawCamera();
		map01 = new Map("../Assets/Map/map.tmx");
		collisionObjects = map01.getCollisionTile(7);
		musicMenu = new BackgroundMusic(volume, "../Assets/Sound/Music/Battle-1.mp3");

		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(map01,7,200,700);
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(layerToRender);
		batch.begin();
		j1.move(map01, collisionObjects);
		KeyEvent.keyPressed(j1);
		j1.draw(batch, stateTime);

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
		camera = new OrthographicCamera();
		camera.setToOrtho(false, windowWidth, windowHeight);
		camera.position.x = 1200;
		camera.position.y = 700;
		camera.update();
	}
}
