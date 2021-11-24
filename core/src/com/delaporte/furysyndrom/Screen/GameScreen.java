package com.delaporte.furysyndrom.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.delaporte.furysyndrom.ui.ResumeButton;
import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Character.Mage;
import com.delaporte.furysyndrom.Sound.BackgroundMusic;
import com.delaporte.furysyndrom.KeyEvent;
import com.delaporte.furysyndrom.Parameter;
import com.delaporte.furysyndrom.Map;

public class GameScreen extends ScreenAdapter {

    FurySyndrom game;
    
	private ButtonSelectorPlayer ButtonSelector4Player;
	private ButtonSelectorPlayer ButtonSelector2Player;
	private Stage stage;
	private int layerToRender[] = { 0, 1, 2, 3, 4, 5, 6};
	private TiledMapRenderer tiledMapRenderer;
    private Map map01;
	private float stateTime;
	private Parameter parameter;
	private Mage j1;
	private KeyEvent KeyEvent = new KeyEvent();
	private BackgroundMusic musicMenu;
	private float volume = 1f;
	private int gameState = 0;
	private boolean isPaused = false;
    private ResumeButton ResumeButton;

    public GameScreen(FurySyndrom game) {
        this.game = game;
		map01 = new Map("../../Assets/Map/map2.tmx");
		musicMenu = new BackgroundMusic(volume, "../../Assets/Sound/Music/Battle-1.mp3");
		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(map01,7,200,700);
        //Menu pause
		stage = new Stage(new ScreenViewport());
        ResumeButton = new ResumeButton("",(int)((game.w-250)/3)*1,(int)((game.h-250)/3)*1,250,250,4, this.game);

    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(
            new InputAdapter() {
                @Override
                public boolean keyDown(int keyCode) {
                    if (keyCode == Input.Keys.ESCAPE) {
                        isPaused = isPaused ? false : true;
                    }
                    return true;
                }
            }
        );
        
        ResumeButton = new ResumeButton("",(int)((game.w-250)/3)*1,(int)((game.h-250)/3)*1,250,250,4, this.game);
		stage.addActor(ResumeButton.getButton());    
    }

    @Override
    public void render(float delta) {
        if(!isPaused){
            stateTime += Gdx.graphics.getDeltaTime();
        }
		Gdx.graphics.setWindowedMode(game.w, game.h);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
		tiledMapRenderer.setView(game.camera);
		tiledMapRenderer.render(layerToRender);
        if(!isPaused){
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                    @Override
                    public boolean keyDown(int keyCode) {
                        System.out.println(keyCode);
                        if (keyCode == Input.Keys.ESCAPE) {
                            isPaused = isPaused ? false : true;
                            // game.setScreen(new PauseScreen(game));
                        }
                        return true;
                    }
                }
            );
            j1.move();
            KeyEvent.keyPressed(j1);
        } else {
            Gdx.input.setInputProcessor(stage);
            isPaused = ResumeButton.IsPaused();
        }
		j1.draw(game.batch, stateTime);
		game.camera.update();
        game.batch.end();	
        if(isPaused){
            stage.act();
            stage.draw();
        }
        ResumeButton.reset();

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