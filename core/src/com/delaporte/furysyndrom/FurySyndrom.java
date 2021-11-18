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
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.Character.Character.CharacterEtat;
import com.delaporte.furysyndrom.Character.Character.CharacterFacing;
import com.delaporte.furysyndrom.Character.Mage;

public class FurySyndrom extends ApplicationAdapter {
	private SpriteBatch batch;
	private Character mario;
	private int layerToRender[] = { 0, 1, 2, 3, 4, 5, 6 };
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
	private Mage j1;
	private int windowWidth = 2000;
	private int windowHeight = 800;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
		batch = new SpriteBatch();
		drawCamera();
		map01 = new Map("../Assets/Map/map.tmx");
		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(0,0);
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render(layerToRender);
		batch.begin();
		if(j1.isJumping()){
			j1.move();		
		}
		keyPressed();
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

	private void keyPressed() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			j1.setFacingToRight();
			j1.move();
		} 
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			j1.setFacingToLeft();
			j1.move();		
		} 
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			j1.setJumping();
			j1.move();
		} 
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			// camera.position.y -= 100 * Gdx.graphics.getDeltaTime();
		} 
		if (Gdx.input.isKeyPressed(Keys.A)) {
		} 
	}
}
