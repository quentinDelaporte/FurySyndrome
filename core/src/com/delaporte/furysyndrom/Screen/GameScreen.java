package com.delaporte.furysyndrom.Screen;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import com.delaporte.furysyndrom.ui.ResumeButton;
import com.delaporte.furysyndrom.ui.ControlsButton;
import com.delaporte.furysyndrom.ui.SoundButton;
import com.delaporte.furysyndrom.ui.ButtonSelectorPlayer;
import com.delaporte.furysyndrom.ui.MusicSlider;
import com.delaporte.furysyndrom.ui.SoundSlider;
import com.delaporte.furysyndrom.ui.BackButton;
import com.delaporte.furysyndrom.ui.HotkeyButton;
import com.delaporte.furysyndrom.Screen.EndGameScreen;
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Character.Mage;
import com.delaporte.furysyndrom.Character.Troll;
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
    private SoundSlider SoundSlider;
    private BackButton BackButton;
    private HotkeyButton hotkeyButton_player1_jump_key;
    private HotkeyButton hotkeyButton_player1_left_key;
    private HotkeyButton hotkeyButton_player1_right_key;
    private HotkeyButton hotkeyButton_player1_run_key;
    private HotkeyButton hotkeyButton_player1_attack_one_key;
    private HotkeyButton hotkeyButton_player1_attack_two_key;
    private HotkeyButton hotkeyButton_player2_jump_key;
    private HotkeyButton hotkeyButton_player2_left_key;
    private HotkeyButton hotkeyButton_player2_right_key;
    private HotkeyButton hotkeyButton_player2_run_key;
    private HotkeyButton hotkeyButton_player2_attack_one_key;
    private HotkeyButton hotkeyButton_player2_attack_two_key;
    private BackButton BackButtonHotkey;
    private Label label_HotkeyButton_player1_jump_key;
    private Label label_HotkeyButton_player1_left_key;
    private Label label_HotkeyButton_player1_right_key;
    private Label label_HotkeyButton_player1_run_key;
    private Label label_HotkeyButton_player1_attack_one_key;
    private Label label_HotkeyButton_player1_attack_two_key;
    private Label label_HotkeyButton_player2_jump_key;
    private Label label_HotkeyButton_player2_left_key;
    private Label label_HotkeyButton_player2_right_key;
    private Label label_HotkeyButton_player2_run_key;
    private Label label_HotkeyButton_player2_attack_one_key;
    private Label label_HotkeyButton_player2_attack_two_key;
    private Label ConfigTitleLabel;
    private Label VolumeConfigTitleLabel;
    private Label labelVolumeMusic;
    private Label labelVolumeSoundEffect;
    private Skin skin = new Skin(Gdx.files.internal("../../Assets/Skin/comic-ui.json"));
    public HotkeyActor selectedHotkeyActor;
    public HotkeyPlayer selectedHotkeyPlayer;
    private ConfigReader ConfigReader = new ConfigReader();
    private ArrayList<Character> characters = new ArrayList<Character>();
    private int col_number = 12;
    private int row_number = 12;
    private int col_width = 0;
    private int row_height = 0;

    public enum HotkeyActor{
        JUMPKEY, LEFTKEY, RIGHTKEY, RUNKEY, ATTACKONEKEY, ATTACKTWOKEY, NONE;
    }

    public enum HotkeyPlayer{
        PLAYER1, PLAYER2, PLAYER3, PLAYER4, NONE;
    }
    
    public GameScreen(FurySyndrom game) {
        this.game = game;
		map01 = new Map("../../Assets/Map/map2.tmx");
		gameMusic = new BackgroundMusic(Float.parseFloat(ConfigReader.getGeneralProperties("Music_Volume")), "../../Assets/Sound/Music/Battle-1.mp3");
        tiledMapRenderer = map01.getTiledMapRenderer();
		parameter = new Parameter();
		j1 = new Mage(map01,7,1600,700);
		j2 = new Troll(map01,7,1500,700);
        characters.add(j1);
        characters.add(j2);
		stagePause = new Stage(new ScreenViewport());
		stageSettings = new Stage(new ScreenViewport());
		stageSoundSettings = new Stage(new ScreenViewport());
        selectedHotkeyActor = HotkeyActor.NONE;
        selectedHotkeyPlayer = HotkeyPlayer.NONE;
        col_width = game.w / col_number;
        row_height = game.h / row_number;
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
        
        LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont Font = new BitmapFont(Gdx.files.internal("../../Assets/Skin/Iosevka-Slab.fnt"));
        labelStyle.font = Font;
        labelStyle.fontColor = Color.WHITE;
        Font.getData().setScale(1.3f);

        LabelStyle TitleStyle = new Label.LabelStyle();
        BitmapFont Font2 = new BitmapFont(Gdx.files.internal("../../Assets/Skin/Iosevka-Slab.fnt"));
        TitleStyle.font = Font2;
        TitleStyle.fontColor = Color.WHITE;
        Font2.getData().setScale(3f);
        
        ResumeButton = new ResumeButton("",5*col_width, 5*row_height, 2*col_width, row_height, this.game);
        ControlsButton = new ControlsButton("",5*col_width, 7*row_height, 2*col_width, row_height, this.game);
        SoundButton = new SoundButton("",5*col_width, 9*row_height, 2*col_width, row_height, this.game);
		stagePause.addActor(ResumeButton.getButton());    
		stagePause.addActor(ControlsButton.getButton());    
		stagePause.addActor(SoundButton.getButton());    

        MusicSlider = new MusicSlider(3*col_width, 6*row_height, 6*col_width, row_height);
        SoundSlider = new SoundSlider(3*col_width, 8*row_height, 6*col_width, row_height);
        BackButton = new BackButton("",4*col_width, 2*row_height, 4*col_width, row_height, this.game);
        labelVolumeMusic = createLabel("Musique", 3*col_width, 7*row_height, 6*col_width, row_height, labelStyle);
        labelVolumeSoundEffect = createLabel("Bruitages", 3*col_width, 9*row_height, 6*col_width, row_height, labelStyle);
        VolumeConfigTitleLabel = createLabel("Volume", 4*col_width, 10*row_height, 4*col_width, 2*row_height, TitleStyle);

        stageSoundSettings.addActor(labelVolumeMusic);
        stageSoundSettings.addActor(labelVolumeSoundEffect);
        stageSoundSettings.addActor(VolumeConfigTitleLabel);
        stageSoundSettings.addActor(MusicSlider.getSlider());
        stageSoundSettings.addActor(SoundSlider.getSlider());
        stageSoundSettings.addActor(BackButton.getButton());

        hotkeyButton_player1_jump_key = new HotkeyButton(2*col_width, 3*row_height, col_width, row_height, this.game,"player1_jump_key");
        hotkeyButton_player1_left_key = new HotkeyButton(2*col_width, 4*row_height, col_width, row_height, this.game,"player1_left_key");
        hotkeyButton_player1_right_key = new HotkeyButton(2*col_width, 5*row_height, col_width, row_height, this.game,"player1_right_key");
        hotkeyButton_player1_run_key = new HotkeyButton(2*col_width, 6*row_height, col_width, row_height, this.game,"player1_run_key");
        hotkeyButton_player1_attack_one_key = new HotkeyButton(2*col_width, 7*row_height, col_width, row_height, this.game,"player1_attack_one_key");
        hotkeyButton_player1_attack_two_key = new HotkeyButton(2*col_width, 8*row_height, col_width, row_height, this.game,"player1_attack_two_key");
        hotkeyButton_player2_jump_key = new HotkeyButton(7*col_width, 3*row_height, col_width, row_height, this.game,"player2_jump_key");
        hotkeyButton_player2_left_key = new HotkeyButton(7*col_width, 4*row_height, col_width, row_height, this.game,"player2_left_key");
        hotkeyButton_player2_right_key = new HotkeyButton(7*col_width, 5*row_height, col_width, row_height, this.game,"player2_right_key");
        hotkeyButton_player2_run_key = new HotkeyButton(7*col_width, 6*row_height, col_width, row_height, this.game,"player2_run_key");
        hotkeyButton_player2_attack_one_key = new HotkeyButton(7*col_width, 7*row_height, col_width, row_height, this.game,"player2_attack_one_key");
        hotkeyButton_player2_attack_two_key = new HotkeyButton(7*col_width, 8*row_height, col_width, row_height, this.game,"player2_attack_two_key");
        BackButtonHotkey = new BackButton("", 4*col_width, 0, col_width*4, row_height, this.game);

        label_HotkeyButton_player1_jump_key = createLabel("Joueur 1: Saut", 4*col_width, 3*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player1_left_key = createLabel("Joueur 1: Droite", 4*col_width, 4*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player1_right_key = createLabel("Joueur 1: Gauche", 4*col_width, 5*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player1_run_key = createLabel("Joueur 1: Courrir", 4*col_width, 6*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player1_attack_one_key = createLabel("Joueur 1: Attaque 1", 4*col_width, 7*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player1_attack_two_key = createLabel("Joueur 1: Attaque 2", 4*col_width, 8*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_jump_key = createLabel("Joueur 2: Saut", 9*col_width, 3*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_left_key = createLabel("Joueur 2: Droite", 9*col_width, 4*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_right_key = createLabel("Joueur 2: Gauche", 9*col_width, 5*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_run_key = createLabel("Joueur 2: Courrir", 9*col_width, 6*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_attack_one_key = createLabel("Joueur 2: Attaque 1", 9*col_width, 7*row_height, 2*col_width, row_height, labelStyle);
        label_HotkeyButton_player2_attack_two_key = createLabel("Joueur 2: Attaque 2", 9*col_width, 8*row_height, 2*col_width, row_height, labelStyle);
        
        ConfigTitleLabel = createLabel("Configuration", 4*col_width, 10*row_height, 4*col_width, 2*row_height, TitleStyle);

        stageSettings.addActor(label_HotkeyButton_player1_jump_key);
        stageSettings.addActor(label_HotkeyButton_player1_left_key);
        stageSettings.addActor(label_HotkeyButton_player1_right_key);
        stageSettings.addActor(label_HotkeyButton_player1_run_key);
        stageSettings.addActor(label_HotkeyButton_player1_attack_one_key);
        stageSettings.addActor(label_HotkeyButton_player1_attack_two_key);
        stageSettings.addActor(label_HotkeyButton_player2_jump_key);
        stageSettings.addActor(label_HotkeyButton_player2_left_key);
        stageSettings.addActor(label_HotkeyButton_player2_right_key);
        stageSettings.addActor(label_HotkeyButton_player2_run_key);
        stageSettings.addActor(label_HotkeyButton_player2_attack_one_key);
        stageSettings.addActor(label_HotkeyButton_player2_attack_two_key);
        stageSettings.addActor(ConfigTitleLabel);

        stageSettings.addActor(BackButtonHotkey.getButton());
        stageSettings.addActor(hotkeyButton_player1_jump_key.getButton());
        stageSettings.addActor(hotkeyButton_player1_left_key.getButton());
        stageSettings.addActor(hotkeyButton_player1_right_key.getButton());
        stageSettings.addActor(hotkeyButton_player1_run_key.getButton());
        stageSettings.addActor(hotkeyButton_player1_attack_one_key.getButton());
        stageSettings.addActor(hotkeyButton_player1_attack_two_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_jump_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_left_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_right_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_run_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_attack_one_key.getButton());
        stageSettings.addActor(hotkeyButton_player2_attack_two_key.getButton());
    }

    @Override
    public void render(float delta) {
		Gdx.graphics.setTitle("FurySyndrom | FPS:" + Gdx.graphics.getFramesPerSecond());
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
                if(ButtonClick(hotkeyButton_player1_jump_key, "player1_jump_key", HotkeyPlayer.PLAYER1, HotkeyActor.JUMPKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_jump_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player1_left_key, "player1_left_key", HotkeyPlayer.PLAYER1, HotkeyActor.LEFTKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_jump_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player1_right_key, "player1_right_key", HotkeyPlayer.PLAYER1, HotkeyActor.RIGHTKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_jump_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player1_run_key, "player1_run_key", HotkeyPlayer.PLAYER1, HotkeyActor.RUNKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_jump_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player1_attack_one_key, "player1_attack_one_key", HotkeyPlayer.PLAYER1, HotkeyActor.ATTACKONEKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_attack_one_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player1_attack_two_key, "player1_attack_two_key", HotkeyPlayer.PLAYER1, HotkeyActor.ATTACKTWOKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player1_attack_two_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_jump_key, "player2_jump_key", HotkeyPlayer.PLAYER2, HotkeyActor.JUMPKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_jump_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_left_key, "player2_left_key", HotkeyPlayer.PLAYER2, HotkeyActor.LEFTKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_left_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_right_key, "player2_right_key", HotkeyPlayer.PLAYER2, HotkeyActor.RIGHTKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_right_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_run_key, "player2_run_key", HotkeyPlayer.PLAYER2, HotkeyActor.RUNKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_run_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_attack_one_key, "player2_attack_one_key", HotkeyPlayer.PLAYER2, HotkeyActor.ATTACKONEKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_attack_one_key.setText();
                }
                else if(ButtonClick(hotkeyButton_player2_attack_two_key, "player2_attack_two_key", HotkeyPlayer.PLAYER2, HotkeyActor.ATTACKTWOKEY)){
                    KeyEvent.reloadConfig();
                    hotkeyButton_player2_attack_two_key.setText();
                }
                else {
                    selectedHotkeyPlayer = HotkeyPlayer.NONE;
                    selectedHotkeyActor = HotkeyActor.NONE;
                }
                if(BackButtonHotkey.goBack()){
                    ControlsButton.reset();
                    BackButtonHotkey.reset();
                    resetAllKeyBindInput();
                    changeEnableStateAllKeyBindButton(true);
                    selectedHotkeyPlayer = HotkeyPlayer.NONE;
                    selectedHotkeyActor = HotkeyActor.NONE;
                    isSettingsOpen = false;
                }
            } else if(isSoundSettingsOpen){
                gameMusic.setVolume(MusicSlider.getVolume());
                updateSoundButton();
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
                        }
                        return true;
                    }
                }
            );
            j1.setCharacters(characters);
            j2.setCharacters(characters);
            j1.move();
            j2.move();
            KeyEvent.keyPressed(j1,j2);
            j1.draw(game.batch, stateTime);
            j2.draw(game.batch, stateTime);
        } else {
            game.batch.draw(new Texture(Gdx.files.internal("../../Assets/pauseBg.jpg")), 0, 0, game.w, game.h);
            if(!isSoundSettingsOpen && !isSettingsOpen){
                Gdx.input.setInputProcessor(stagePause);
            } else if (isSoundSettingsOpen){
                Gdx.input.setInputProcessor(stageSoundSettings);               
            } else if (isSettingsOpen){
                Gdx.input.setInputProcessor(stageSettings);
            }
            isPaused = ResumeButton.IsPaused();
        }

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
        if(j1.isDead() || j2.isDead()){
            game.setScreen(new EndGameScreen(game, (j1.isDead() ? j2 : j1)));
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

    public void resetAllKeyBindInput(){
        hotkeyButton_player1_jump_key.reset();
        hotkeyButton_player1_left_key.reset();
        hotkeyButton_player1_right_key.reset();
        hotkeyButton_player1_run_key.reset();
        hotkeyButton_player1_attack_one_key.reset();
        hotkeyButton_player1_attack_two_key.reset();
        hotkeyButton_player2_jump_key.reset();
        hotkeyButton_player2_left_key.reset();
        hotkeyButton_player2_right_key.reset();
        hotkeyButton_player2_run_key.reset();
        hotkeyButton_player2_attack_one_key.reset();
        hotkeyButton_player2_attack_two_key.reset();
    }

    public void changeEnableStateAllKeyBindButton(boolean state){
        hotkeyButton_player1_jump_key.changeEnabled(state);
        hotkeyButton_player1_left_key.changeEnabled(state);
        hotkeyButton_player1_right_key.changeEnabled(state);
        hotkeyButton_player1_run_key.changeEnabled(state);
        hotkeyButton_player1_attack_one_key.changeEnabled(state);
        hotkeyButton_player1_attack_two_key.changeEnabled(state);
        hotkeyButton_player2_jump_key.changeEnabled(state);
        hotkeyButton_player2_left_key.changeEnabled(state);
        hotkeyButton_player2_right_key.changeEnabled(state);
        hotkeyButton_player2_run_key.changeEnabled(state);
        hotkeyButton_player2_attack_one_key.changeEnabled(state);
        hotkeyButton_player2_attack_two_key.changeEnabled(state);
    }

    public boolean ButtonClick(HotkeyButton HotkeyButton, String s, HotkeyPlayer player, HotkeyActor actor){
        if(HotkeyButton.isActive()){
            if(HotkeyButton.isEnabled()){
                selectedHotkeyPlayer = player;
                selectedHotkeyActor = actor;
                changeEnableStateAllKeyBindButton(false);
                HotkeyButton.changeEnabled(true);
                if(KeyEvent.isKeyPressed(111)){
                } else if(KeyEvent.isAnyKeyPressed()){
                    ConfigReader.updateKeyProperties(s, KeyEvent.getKeyCodePressed() + "");
                    System.out.println(s + KeyEvent.getKeyCodePressed());
                }
                if(KeyEvent.isAnyKeyPressed()){
                    resetAllKeyBindInput();
                    changeEnableStateAllKeyBindButton(true);
                    selectedHotkeyPlayer = HotkeyPlayer.NONE;
                    selectedHotkeyActor = HotkeyActor.NONE;
                }
            }
            return true;
        } 
        return false;
    }

    public Label createLabel(String text, int x, int y,  int width, int height, LabelStyle labelStyle){
        Label label = new Label(text, labelStyle);
        label.setSize(width, height);
        label.setPosition(x, y);
        label.setWrap(true);
        return label;
    }

    private void updateSoundButton(){
        ResumeButton.updateVolume();
        ControlsButton.updateVolume();
        BackButton.updateVolume();
        hotkeyButton_player1_jump_key.updateVolume();
        hotkeyButton_player1_left_key.updateVolume();
        hotkeyButton_player1_right_key.updateVolume();
        hotkeyButton_player1_run_key.updateVolume();
        hotkeyButton_player1_attack_one_key.updateVolume();
        hotkeyButton_player1_attack_two_key.updateVolume();
        hotkeyButton_player2_jump_key.updateVolume();
        hotkeyButton_player2_left_key.updateVolume();
        hotkeyButton_player2_right_key.updateVolume();
        hotkeyButton_player2_run_key.updateVolume();
        hotkeyButton_player2_attack_one_key.updateVolume();
        hotkeyButton_player2_attack_two_key.updateVolume();
        BackButtonHotkey.updateVolume();
    }

}