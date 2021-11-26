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
import com.delaporte.furysyndrom.ui.ControlsButton;
import com.delaporte.furysyndrom.ui.SoundButton;
import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.delaporte.furysyndrom.ui.MusicSlider;
import com.delaporte.furysyndrom.ui.BackButton;
import com.delaporte.furysyndrom.ui.HotkeyButton;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Character.Mage;
import com.delaporte.furysyndrom.Character.Ninja;
import com.delaporte.furysyndrom.Character.Archer;
import com.delaporte.furysyndrom.Character.Guerrier;
import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.Sound.BackgroundMusic;
import com.delaporte.furysyndrom.KeyEvent;
import com.delaporte.furysyndrom.Parameter;
import com.delaporte.furysyndrom.Map;
import com.delaporte.furysyndrom.utils.ConfigReader;

public class GameScreen extends ScreenAdapter {

    FurySyndrom game;
    
	private ButtonSelectorPlayer ButtonSelector4Player;
	private ButtonSelectorPlayer ButtonSelector2Player;
	private Stage stagePause;
	private Stage stageSettings;
	private Stage stageSoundSettings;
	private int layerToRender[] = { 0, 1, 2, 3, 4, 5, 6};
	private TiledMapRenderer tiledMapRenderer;
    private Map map01;
	private float stateTime;
	private Parameter parameter;
	private Character j1;
	private Character j2;
	private KeyEvent KeyEvent = new KeyEvent();
	private BackgroundMusic gameMusic;
	private float volume = 1f;
	private int gameState = 0;
	private boolean isPaused = false;
	private boolean isSettingsOpen = false;
	private boolean isSoundSettingsOpen = false;
    private ResumeButton ResumeButton;
    private ControlsButton ControlsButton;
    private SoundButton SoundButton;
    private MusicSlider MusicSlider;
    private MusicSlider SoundSlider;
    private BackButton BackButton;

    private HotkeyButton HotkeyButton_player1_jump_key;
    private HotkeyButton HotkeyButton_player1_left_key;
    private HotkeyButton HotkeyButton_player1_right_key;
    private HotkeyButton HotkeyButton_player1_run_key;
    private HotkeyButton HotkeyButton_player1_attack_one_key;
    private HotkeyButton HotkeyButton_player1_attack_two_key;
    private HotkeyButton HotkeyButton_player2_jump_key;
    private HotkeyButton HotkeyButton_player2_left_key;
    private HotkeyButton HotkeyButton_player2_right_key;
    private HotkeyButton HotkeyButton_player2_run_key;
    private HotkeyButton HotkeyButton_player2_attack_one_key;
    private HotkeyButton HotkeyButton_player2_attack_two_key;

    private ConfigReader ConfigReader = new ConfigReader();
    
    public GameScreen(FurySyndrom game) {
        this.game = game;
		map01 = new Map("../../Assets/Map/map2.tmx");
		gameMusic = new BackgroundMusic(Float.parseFloat(ConfigReader.getGeneralProperties("Music_Volume")), "../../Assets/Sound/Music/Battle-1.mp3");
		tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(map01,7,1600,700);
		j2 = new Ninja(map01,7,200,700);
        //Menu pause
		stagePause = new Stage(new ScreenViewport());
		stageSettings = new Stage(new ScreenViewport());
		stageSoundSettings = new Stage(new ScreenViewport());
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
        
        ResumeButton = new ResumeButton("",(int)((game.w-250)/2),(int)((game.h-250)/8)*1,250,250, this.game);
        ControlsButton = new ControlsButton("",(int)((game.w-250)/2),(int)((game.h-250)/8)*4,250,250, this.game);
        SoundButton = new SoundButton("",(int)((game.w-250)/2),(int)((game.h-250)/8)*7,250,250, this.game);
		stagePause.addActor(ResumeButton.getButton());    
		stagePause.addActor(ControlsButton.getButton());    
		stagePause.addActor(SoundButton.getButton());    

        MusicSlider = new MusicSlider((int)((game.w-500)/2),(int)((game.h-20)/4)*2,500,20, gameMusic.getVolume());
        gameMusic.setVolume(MusicSlider.getVolume());
        
        SoundSlider = new MusicSlider((int)((game.w-500)/2),(int)((game.h-20)/4)*3,500,20, 0f);
        // sound.setVolume(SoundSlider.getVolume());

        BackButton = new BackButton("",(int)((game.w-250)/2),(int)((game.h-250)/8)*1,250,250, this.game);

        stageSoundSettings.addActor(MusicSlider.getSlider());
        stageSoundSettings.addActor(SoundSlider.getSlider());
        stageSoundSettings.addActor(BackButton.getButton());

        HotkeyButton_player1_jump_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*1,80,80, this.game,"player1_jump_key");
        HotkeyButton_player1_left_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*2,80,80, this.game,"player1_left_key");
        HotkeyButton_player1_right_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*3,80,80, this.game,"player1_right_key");
        HotkeyButton_player1_run_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*4,80,80, this.game,"player1_run_key");
        HotkeyButton_player1_attack_one_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*5,80,80, this.game,"player1_attack_one_key");
        HotkeyButton_player1_attack_two_key = new HotkeyButton("",(int)(((game.w-80)/4)*0.5),(int)((game.h-80)/8)*6,80,80, this.game,"player1_attack_two_key");
        HotkeyButton_player2_jump_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*1,80,80, this.game,"player2_jump_key");
        HotkeyButton_player2_left_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*2,80,80, this.game,"player2_left_key");
        HotkeyButton_player2_right_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*3,80,80, this.game,"player2_right_key");
        HotkeyButton_player2_run_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*4,80,80, this.game,"player2_run_key");
        HotkeyButton_player2_attack_one_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*5,80,80, this.game,"player2_attack_one_key");
        HotkeyButton_player2_attack_two_key = new HotkeyButton("",(int)(((game.w-80)/4)*2.5),(int)((game.h-80)/8)*6,80,80, this.game,"player2_attack_two_key");

        stageSettings.addActor(HotkeyButton_player1_jump_key.getButton());
        stageSettings.addActor(HotkeyButton_player1_left_key.getButton());
        stageSettings.addActor(HotkeyButton_player1_right_key.getButton());
        stageSettings.addActor(HotkeyButton_player1_run_key.getButton());
        stageSettings.addActor(HotkeyButton_player1_attack_one_key.getButton());
        stageSettings.addActor(HotkeyButton_player1_attack_two_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_jump_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_left_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_right_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_run_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_attack_one_key.getButton());
        stageSettings.addActor(HotkeyButton_player2_attack_two_key.getButton());
    }

    @Override
    public void render(float delta) {
        if(isPaused){
            if(ControlsButton.isSettingsOpen())
                isSettingsOpen = true;
            else 
                isSettingsOpen = false;
            if(SoundButton.isSoundSettingsOpen())
                isSoundSettingsOpen = true;
            else 
                isSoundSettingsOpen = false;
            if(isSettingsOpen){
            } else if(isSoundSettingsOpen){
                gameMusic.setVolume(MusicSlider.getVolume());
                // gameMusic.setVolume(SoundSlider.getVolume());
                ConfigReader.updateGeneralProperties("Music_Volume", MusicSlider.getVolume() + "");
                ConfigReader.updateGeneralProperties("Sound_Volume", SoundSlider.getVolume() + "");
                if(BackButton.goBack())
                    SoundButton.reset();
                    BackButton.reset();
            } else {
            }
        }
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
            j2.move();
            KeyEvent.keyPressed(j1,j2);
        } else {
            if(!isSoundSettingsOpen && !isSettingsOpen){
                Gdx.input.setInputProcessor(stagePause);
            } else if (isSoundSettingsOpen){
                Gdx.input.setInputProcessor(stageSoundSettings);               
            } else if (isSettingsOpen){
                Gdx.input.setInputProcessor(stageSettings);
            }
            isPaused = ResumeButton.IsPaused();
        }
		j1.draw(game.batch, stateTime);
		j2.draw(game.batch, stateTime);
		game.camera.update();
        game.batch.end();	
        if(isPaused){
            if(!isSoundSettingsOpen && !isSettingsOpen){
                stagePause.act();
                stagePause.draw();
            } else if(isSoundSettingsOpen){
                stageSoundSettings.act();
                stageSoundSettings.draw();
            }else if(isSettingsOpen){
                stageSettings.act();
                stageSettings.draw();
            }
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