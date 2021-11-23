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
import com.delaporte.furysyndrom.gui.MainScreen;
import com.delaporte.furysyndrom.gui.PlayerNumberSelectorScreen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;


public class FurySyndrom extends ApplicationAdapter {
	private SpriteBatch batch;
	private int layerToRender[] = { 0, 1, 2, 3, 4, 5, 6};
	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;
	private Map map01;
	private float stateTime;
	private Parameter parameter;
	private Mage j1;
	private int windowWidth = 1920;
	private int windowHeight = 800;
	private KeyEvent KeyEvent = new KeyEvent();
	private BackgroundMusic musicMenu;
	private float volume = 1f;
	private int gameState = 0;
	private MainScreen mainScreen;
	private PlayerNumberSelectorScreen playerNumberSelectorScreen;
	private int nbPlayer = 0;

	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(windowWidth, windowHeight);
		batch = new SpriteBatch();
		drawCamera();
		map01 = new Map("../../Assets/Map/map2.tmx");
		musicMenu = new BackgroundMusic(volume, "../../Assets/Sound/Music/Battle-1.mp3");
		mainScreen = new MainScreen(windowWidth, windowHeight, new Texture(Gdx.files.internal("../../Assets/MainImage.png")));
		playerNumberSelectorScreen = new PlayerNumberSelectorScreen(windowWidth, windowHeight, new Texture(Gdx.files.internal("../../Assets/HowManyPlayer.png")));
		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(map01,7,200,700);
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.graphics.setTitle("FurySyndrom | FPS:" + Gdx.graphics.getFramesPerSecond());
		batch.begin();
		if(gameState == 0) {
			mainScreen.draw(batch, windowWidth, windowHeight);
			if(KeyEvent.isAnyKeyPressed()){
				gameState = 1;
			}
		} else if(gameState == 1){
			playerNumberSelectorScreen.draw(batch, windowWidth, windowHeight);
			if(playerNumberSelectorScreen.getNbPlayer() != 0){
				nbPlayer = playerNumberSelectorScreen.getNbPlayer();
				gameState = 3;
			}
		} else if(gameState == 2){
		} else if(gameState == 3){
			tiledMapRenderer.setView(camera);
			tiledMapRenderer.render(layerToRender);
			j1.move();
			KeyEvent.keyPressed(j1);
			j1.draw(batch, stateTime);
		} 
		camera.update();
		batch.end();
		if(gameState == 1){
			playerNumberSelectorScreen.drawScene();
		}
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
		camera.position.x = 1920/2;
		camera.position.y = 800/2;
		camera.update();
	}
}
