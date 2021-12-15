package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.delaporte.furysyndrom.utils.ConfigReader;

public class SoundSlider {
    public float volume;
    private Slider slider;
    private Skin skin;
    private ConfigReader configReader = new ConfigReader();

    public SoundSlider(int x, int y, int width, int height) {
        skin = new Skin(Gdx.files.internal("../assets/Skin/comic-ui.json"));
        slider = new Slider(0, 1, 0.1f, false, skin);
        slider.setSize(width, height);
        slider.setPosition(x, y);
        slider.setVisualPercent(Float.parseFloat(configReader.getGeneralProperties("Sound_Volume")));
        this.volume = Float.parseFloat(configReader.getGeneralProperties("Sound_Volume"));
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volume = (slider.getValue() / 100f) * 100;
                configReader.updateGeneralProperties("Sound_Volume", volume + "");
            }
        });
    }

    public float getVolume() {
        return volume;
    }

    public Slider getSlider(){
        return slider;
    }
}

