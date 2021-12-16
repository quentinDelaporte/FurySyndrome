package com.delaporte.furysyndrom.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BackgroundMusic {
    private Music music;
    private float volume;

    public BackgroundMusic(float volume, String path) {
        this.volume = volume;
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(true);
        music.setVolume(volume);
        music.play();
    }

    public void setVolume(float volume) {
        this.volume = volume;
        music.setVolume(volume);
    }

    public void stop() {
        music.stop();
    }

    public float getVolume(){
        return volume;
    }
}
