package com.delaporte.furysyndrom.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class MusicSlider {
    public float volume;
    private Slider slider;
    private Skin skin;

    public MusicSlider(int x, int y, int width, int height, float vol) {
        skin = new Skin(Gdx.files.internal("../../Assets/Skin/comic-ui.json"));

        slider = new Slider(0, 1, 0.1f, false, skin);
        slider.setSize(width, height);
        slider.setPosition(x, y);
        slider.setVisualPercent(vol);
        this.volume = vol;
        slider.addListener(new ChangeListener() {
    
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                volume = (slider.getValue() / 100f) * 100;
                System.out.println(volume);
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

