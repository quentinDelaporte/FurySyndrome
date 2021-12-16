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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
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
import com.delaporte.furysyndrom.FurySyndrom;
import com.delaporte.furysyndrom.Character.Mage;
import com.delaporte.furysyndrom.Character.Troll;
import com.delaporte.furysyndrom.Character.Character;
import com.delaporte.furysyndrom.Sound.BackgroundMusic;
import com.delaporte.furysyndrom.KeyEvent;
import com.delaporte.furysyndrom.Map;
import com.delaporte.furysyndrom.utils.ConfigReader;
import com.delaporte.furysyndrom.utils.HotkeyActor;
import com.delaporte.furysyndrom.utils.HotkeyPlayer;

public class GameScreen extends ScreenAdapter {

    FurySyndrom game;
    
	private ButtonSelectorPlayer buttonSelector4Player;
	private ButtonSelectorPlayer buttonSelector2Player;
	private Stage stagePause;
	private Stage stageSettings;
	private Stage stageSoundSettings;
	private int[] layerToRender = { 0, 1, 2, 3, 4, 5, 6};
	private TiledMapRenderer tiledMapRenderer;
    private Map map01;
	private float stateTime;
	private Character j1;
	private Character j2;
	private KeyEvent keyEvent = new KeyEvent();
	private BackgroundMusic gameMusic;
	private boolean isPaused = false;
	private boolean isSettingsOpen = false;
	private boolean isSoundSettingsOpen = false;
    private ResumeButton resumeButton;
    private ControlsButton controlsButton;
    private SoundButton soundButton;
    private MusicSlider musicSlider;
    private SoundSlider soundSlider;
    private BackButton backButton;
    private HotkeyButton hotkeyButtonPlayer1JumpKey;
    private HotkeyButton hotkeyButtonPlayer1LeftKey;
    private HotkeyButton hotkeyButtonPlayer1RightKey;
    private HotkeyButton hotkeyButtonPlayer1RunKey;
    private HotkeyButton hotkeyButtonPlayer1AttackOneKey;
    private HotkeyButton hotkeyButtonPlayer1AttackTwoKey;
    private HotkeyButton hotkeyButtonPlayer2JumpKey;
    private HotkeyButton hotkeyButtonPlayer2LeftKey;
    private HotkeyButton hotkeyButtonPlayer2RightKey;
    private HotkeyButton hotkeyButtonPlayer2RunKey;
    private HotkeyButton hotkeyButtonPlayer2AttackOneKey;
    private HotkeyButton hotkeyButtonPlayer2AttackTwoKey;
    private BackButton backButtonHotkey;
    private Label labelHotkeyButtonPlayer1JumpKey;
    private Label labelHotkeyButtonPlayer1LeftKey;
    private Label labelHotkeyButtonPlayer1RightKey;
    private Label labelHotkeyButtonPlayer1RunKey;
    private Label labelHotkeyButtonPlayer1AttackOneKey;
    private Label labelHotkeyButtonPlayer1AttackTwoKey;
    private Label labelHotkeyButtonPlayer2JumpKey;
    private Label labelHotkeyButtonPlayer2LeftKey;
    private Label labelHotkeyButtonPlayer2RightKey;
    private Label labelHotkeyButtonPlayer2RunKey;
    private Label labelHotkeyButtonPlayer2AttackOneKey;
    private Label labelHotkeyButtonPlayer2AttackTwoKey;
    private Label ConfigTitleLabel;
    private Label VolumeConfigTitleLabel;
    private Label labelVolumeMusic;
    private Label labelVolumeSoundEffect;
    public HotkeyActor selectedHotkeyActor;
    public HotkeyPlayer selectedHotkeyPlayer;
    private ConfigReader configReader = new ConfigReader();
    private ArrayList<Character> characters = new ArrayList<>();
    private int colNumber = 12;
    private int rowNumber = 12;
    private int colWidth = 0;
    private int rowHeight = 0;
    private LabelStyle labelStyle = new Label.LabelStyle();
    private LabelStyle titleStyle = new Label.LabelStyle();
    private BitmapFont font;
    private BitmapFont font2;
    
    public GameScreen(FurySyndrom game) {
        this.game = game;
		map01 = new Map("../assets/Map/map2.tmx");
		gameMusic = new BackgroundMusic(Float.parseFloat(configReader.getGeneralProperties("Music_Volume")), "../assets/Sound/Music/Battle-1.mp3");
        tiledMapRenderer = map01.getTiledMapRenderer();
		j1 = new Mage(map01,7,1600,700);
		j2 = new Troll(map01,7,1500,700);
        characters.add(j1);
        characters.add(j2);
		stagePause = new Stage(new ScreenViewport());
		stageSettings = new Stage(new ScreenViewport());
		stageSoundSettings = new Stage(new ScreenViewport());
        selectedHotkeyActor = HotkeyActor.NONE;
        selectedHotkeyPlayer = HotkeyPlayer.NONE;
        colWidth = game.w / colNumber;
        rowHeight = game.h / rowNumber;

        font = new BitmapFont(Gdx.files.internal("../assets/Skin/Iosevka-Slab.fnt"));
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        font.getData().setScale(1.3f);

        font2 = new BitmapFont(Gdx.files.internal("../assets/Skin/Iosevka-Slab.fnt"));
        titleStyle.font = font2;
        titleStyle.fontColor = Color.WHITE;
        font2.getData().setScale(3f);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(
            new InputAdapter() {
                @Override
                public boolean keyDown(int keyCode) {
                    if (keyCode == Input.Keys.ESCAPE) {
                        isPaused = !isPaused;
                    }
                    return true;
                }
            }
        );
        
        resumeButton = new ResumeButton("",5*colWidth, 5*rowHeight, 2*colWidth, rowHeight, this.game);
        controlsButton = new ControlsButton("",5*colWidth, 7*rowHeight, 2*colWidth, rowHeight, this.game);
        soundButton = new SoundButton("",5*colWidth, 9*rowHeight, 2*colWidth, rowHeight, this.game);
		stagePause.addActor(resumeButton.getButton());    
		stagePause.addActor(controlsButton.getButton());    
		stagePause.addActor(soundButton.getButton());    

        musicSlider = new MusicSlider(3*colWidth, 6*rowHeight, 6*colWidth, rowHeight);
        soundSlider = new SoundSlider(3*colWidth, 8*rowHeight, 6*colWidth, rowHeight);
        backButton = new BackButton("",4*colWidth, 2*rowHeight, 4*colWidth, rowHeight, this.game);
        labelVolumeMusic = createLabel("Musique", 3*colWidth, 7*rowHeight, 6*colWidth, rowHeight, labelStyle);
        labelVolumeSoundEffect = createLabel("Bruitages", 3*colWidth, 9*rowHeight, 6*colWidth, rowHeight, labelStyle);
        VolumeConfigTitleLabel = createLabel("Volume", 4*colWidth, 10*rowHeight, 4*colWidth, 2*rowHeight, titleStyle);

        stageSoundSettings.addActor(labelVolumeMusic);
        stageSoundSettings.addActor(labelVolumeSoundEffect);
        stageSoundSettings.addActor(VolumeConfigTitleLabel);
        stageSoundSettings.addActor(musicSlider.getSlider());
        stageSoundSettings.addActor(soundSlider.getSlider());
        stageSoundSettings.addActor(backButton.getButton());

        hotkeyButtonPlayer1JumpKey = new HotkeyButton(2*colWidth, 3*rowHeight, colWidth, rowHeight, this.game,"player1_jump_key");
        hotkeyButtonPlayer1LeftKey = new HotkeyButton(2*colWidth, 4*rowHeight, colWidth, rowHeight, this.game,"player1_left_key");
        hotkeyButtonPlayer1RightKey = new HotkeyButton(2*colWidth, 5*rowHeight, colWidth, rowHeight, this.game,"player1_right_key");
        hotkeyButtonPlayer1RunKey = new HotkeyButton(2*colWidth, 6*rowHeight, colWidth, rowHeight, this.game,"player1_run_key");
        hotkeyButtonPlayer1AttackOneKey = new HotkeyButton(2*colWidth, 7*rowHeight, colWidth, rowHeight, this.game,"player1_attack_one_key");
        hotkeyButtonPlayer1AttackTwoKey = new HotkeyButton(2*colWidth, 8*rowHeight, colWidth, rowHeight, this.game,"player1_attack_two_key");
        hotkeyButtonPlayer2JumpKey = new HotkeyButton(7*colWidth, 3*rowHeight, colWidth, rowHeight, this.game,"player2_jump_key");
        hotkeyButtonPlayer2LeftKey = new HotkeyButton(7*colWidth, 4*rowHeight, colWidth, rowHeight, this.game,"player2_left_key");
        hotkeyButtonPlayer2RightKey = new HotkeyButton(7*colWidth, 5*rowHeight, colWidth, rowHeight, this.game,"player2_right_key");
        hotkeyButtonPlayer2RunKey = new HotkeyButton(7*colWidth, 6*rowHeight, colWidth, rowHeight, this.game,"player2_run_key");
        hotkeyButtonPlayer2AttackOneKey = new HotkeyButton(7*colWidth, 7*rowHeight, colWidth, rowHeight, this.game,"player2_attack_one_key");
        hotkeyButtonPlayer2AttackTwoKey = new HotkeyButton(7*colWidth, 8*rowHeight, colWidth, rowHeight, this.game,"player2_attack_two_key");
        backButtonHotkey = new BackButton("", 4*colWidth, 0, colWidth*4, rowHeight, this.game);

        labelHotkeyButtonPlayer1JumpKey = createLabel("Joueur 1: Saut", 4*colWidth, 3*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer1LeftKey = createLabel("Joueur 1: Gauche", 4*colWidth, 4*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer1RightKey = createLabel("Joueur 1: Droite", 4*colWidth, 5*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer1RunKey = createLabel("Joueur 1: Courrir", 4*colWidth, 6*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer1AttackOneKey = createLabel("Joueur 1: Attaque 1", 4*colWidth, 7*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer1AttackTwoKey = createLabel("Joueur 1: Attaque 2", 4*colWidth, 8*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2JumpKey = createLabel("Joueur 2: Saut", 9*colWidth, 3*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2LeftKey = createLabel("Joueur 2: Gauche", 9*colWidth, 4*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2RightKey = createLabel("Joueur 2: Droite", 9*colWidth, 5*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2RunKey = createLabel("Joueur 2: Courrir", 9*colWidth, 6*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2AttackOneKey = createLabel("Joueur 2: Attaque 1", 9*colWidth, 7*rowHeight, 2*colWidth, rowHeight, labelStyle);
        labelHotkeyButtonPlayer2AttackTwoKey = createLabel("Joueur 2: Attaque 2", 9*colWidth, 8*rowHeight, 2*colWidth, rowHeight, labelStyle);
        
        ConfigTitleLabel = createLabel("Configuration", 4*colWidth, 10*rowHeight, 4*colWidth, 2*rowHeight, titleStyle);

        stageSettings.addActor(labelHotkeyButtonPlayer1JumpKey);
        stageSettings.addActor(labelHotkeyButtonPlayer1LeftKey);
        stageSettings.addActor(labelHotkeyButtonPlayer1RightKey);
        stageSettings.addActor(labelHotkeyButtonPlayer1RunKey);
        stageSettings.addActor(labelHotkeyButtonPlayer1AttackOneKey);
        stageSettings.addActor(labelHotkeyButtonPlayer1AttackTwoKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2JumpKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2LeftKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2RightKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2RunKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2AttackOneKey);
        stageSettings.addActor(labelHotkeyButtonPlayer2AttackTwoKey);
        stageSettings.addActor(ConfigTitleLabel);

        stageSettings.addActor(backButtonHotkey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1JumpKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1LeftKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1RightKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1RunKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1AttackOneKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer1AttackTwoKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2JumpKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2LeftKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2RightKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2RunKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2AttackOneKey.getButton());
        stageSettings.addActor(hotkeyButtonPlayer2AttackTwoKey.getButton());
    }

    @Override
    public void render(float delta) {
		Gdx.graphics.setTitle("FurySyndrom | FPS:" + Gdx.graphics.getFramesPerSecond());
        if(isPaused){
            if(controlsButton.isSettingsOpen())
                isSettingsOpen = true;
            else 
                isSettingsOpen = false;

            if(soundButton.isSoundSettingsOpen())
                isSoundSettingsOpen = true;
            else 
                isSoundSettingsOpen = false;

            if(isSettingsOpen){
                if(ButtonClick(hotkeyButtonPlayer1JumpKey, "player1_jump_key", HotkeyPlayer.PLAYER1, HotkeyActor.JUMPKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1JumpKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer1LeftKey, "player1_left_key", HotkeyPlayer.PLAYER1, HotkeyActor.LEFTKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1LeftKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer1RightKey, "player1_right_key", HotkeyPlayer.PLAYER1, HotkeyActor.RIGHTKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1RightKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer1RunKey, "player1_run_key", HotkeyPlayer.PLAYER1, HotkeyActor.RUNKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1RunKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer1AttackOneKey, "player1_attack_one_key", HotkeyPlayer.PLAYER1, HotkeyActor.ATTACKONEKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1AttackOneKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer1AttackTwoKey, "player1_attack_two_key", HotkeyPlayer.PLAYER1, HotkeyActor.ATTACKTWOKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer1AttackTwoKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2JumpKey, "player2_jump_key", HotkeyPlayer.PLAYER2, HotkeyActor.JUMPKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2JumpKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2LeftKey, "player2_left_key", HotkeyPlayer.PLAYER2, HotkeyActor.LEFTKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2LeftKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2RightKey, "player2_right_key", HotkeyPlayer.PLAYER2, HotkeyActor.RIGHTKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2RightKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2RunKey, "player2_run_key", HotkeyPlayer.PLAYER2, HotkeyActor.RUNKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2RunKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2AttackOneKey, "player2_attack_one_key", HotkeyPlayer.PLAYER2, HotkeyActor.ATTACKONEKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2AttackOneKey.setText();
                }
                else if(ButtonClick(hotkeyButtonPlayer2AttackTwoKey, "player2_attack_two_key", HotkeyPlayer.PLAYER2, HotkeyActor.ATTACKTWOKEY)){
                    keyEvent.reloadConfig();
                    hotkeyButtonPlayer2AttackTwoKey.setText();
                }
                else {
                    selectedHotkeyPlayer = HotkeyPlayer.NONE;
                    selectedHotkeyActor = HotkeyActor.NONE;
                }
                if(backButtonHotkey.goBack()){
                    controlsButton.reset();
                    backButtonHotkey.reset();
                    resetAllKeyBindInput();
                    changeEnableStateAllKeyBindButton(true);
                    selectedHotkeyPlayer = HotkeyPlayer.NONE;
                    selectedHotkeyActor = HotkeyActor.NONE;
                    isSettingsOpen = false;
                }
            } else if(isSoundSettingsOpen){
                gameMusic.setVolume(musicSlider.getVolume());
                updateSoundButton();
                if(backButton.goBack())
                    soundButton.reset();
                backButton.reset();
            }
        }
        if(!isPaused) stateTime += Gdx.graphics.getDeltaTime();
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
                        if (keyCode == Input.Keys.ESCAPE) {
                            isPaused = !isPaused;
                        }
                        return true;
                    }
                }
            );
            j1.setCharacters(characters);
            j2.setCharacters(characters);
            j1.move();
            j2.move();
            keyEvent.keyPressed(j1,j2);
            j1.draw(game.batch, stateTime);
            j2.draw(game.batch, stateTime);
        } else {
            game.batch.draw(new Texture(Gdx.files.internal("../assets/pauseBg.jpg")), 0, 0, game.w, game.h);
            if(!isSoundSettingsOpen && !isSettingsOpen){
                Gdx.input.setInputProcessor(stagePause);
            } else if (isSoundSettingsOpen){
                Gdx.input.setInputProcessor(stageSoundSettings);               
            } else if (isSettingsOpen){
                Gdx.input.setInputProcessor(stageSettings);
            }
            isPaused = resumeButton.IsPaused();
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
        resumeButton.reset();
        if(j1.isDead() || j2.isDead()){
            gameMusic.stop();
            gameMusic = null;
            game.setScreen(new EndGameScreen(game, (j1.isDead() ? j2 : j1)));
        }
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    public int getNbPlayer(){
		return (
			buttonSelector2Player.getNbPlayer() == 0) 
			? buttonSelector4Player.getNbPlayer() : 
			(buttonSelector2Player.getNbPlayer());
	}

    public void resetAllKeyBindInput(){
        hotkeyButtonPlayer1JumpKey.reset();
        hotkeyButtonPlayer1LeftKey.reset();
        hotkeyButtonPlayer1RightKey.reset();
        hotkeyButtonPlayer1RunKey.reset();
        hotkeyButtonPlayer1AttackOneKey.reset();
        hotkeyButtonPlayer1AttackTwoKey.reset();
        hotkeyButtonPlayer2JumpKey.reset();
        hotkeyButtonPlayer2LeftKey.reset();
        hotkeyButtonPlayer2RightKey.reset();
        hotkeyButtonPlayer2RunKey.reset();
        hotkeyButtonPlayer2AttackOneKey.reset();
        hotkeyButtonPlayer2AttackTwoKey.reset();
    }

    public void changeEnableStateAllKeyBindButton(boolean state){
        hotkeyButtonPlayer1JumpKey.changeEnabled(state);
        hotkeyButtonPlayer1LeftKey.changeEnabled(state);
        hotkeyButtonPlayer1RightKey.changeEnabled(state);
        hotkeyButtonPlayer1RunKey.changeEnabled(state);
        hotkeyButtonPlayer1AttackOneKey.changeEnabled(state);
        hotkeyButtonPlayer1AttackTwoKey.changeEnabled(state);
        hotkeyButtonPlayer2JumpKey.changeEnabled(state);
        hotkeyButtonPlayer2LeftKey.changeEnabled(state);
        hotkeyButtonPlayer2RightKey.changeEnabled(state);
        hotkeyButtonPlayer2RunKey.changeEnabled(state);
        hotkeyButtonPlayer2AttackOneKey.changeEnabled(state);
        hotkeyButtonPlayer2AttackTwoKey.changeEnabled(state);
    }

    public boolean ButtonClick(HotkeyButton hotkeyButton, String s, HotkeyPlayer player, HotkeyActor actor){
        if(hotkeyButton.isActive()){
            if(hotkeyButton.isEnabled()){
                selectedHotkeyPlayer = player;
                selectedHotkeyActor = actor;
                changeEnableStateAllKeyBindButton(false);
                hotkeyButton.changeEnabled(true);
                if(!keyEvent.isKeyPressed(111) && keyEvent.isAnyKeyPressed())
                    configReader.updateKeyProperties(s, keyEvent.getKeyCodePressed() + "");
                if(keyEvent.isAnyKeyPressed()){
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
        resumeButton.updateVolume();
        controlsButton.updateVolume();
        backButton.updateVolume();
        hotkeyButtonPlayer1JumpKey.updateVolume();
        hotkeyButtonPlayer1LeftKey.updateVolume();
        hotkeyButtonPlayer1RightKey.updateVolume();
        hotkeyButtonPlayer1RunKey.updateVolume();
        hotkeyButtonPlayer1AttackOneKey.updateVolume();
        hotkeyButtonPlayer1AttackTwoKey.updateVolume();
        hotkeyButtonPlayer2JumpKey.updateVolume();
        hotkeyButtonPlayer2LeftKey.updateVolume();
        hotkeyButtonPlayer2RightKey.updateVolume();
        hotkeyButtonPlayer2RunKey.updateVolume();
        hotkeyButtonPlayer2AttackOneKey.updateVolume();
        hotkeyButtonPlayer2AttackTwoKey.updateVolume();
        backButtonHotkey.updateVolume();
    }

}