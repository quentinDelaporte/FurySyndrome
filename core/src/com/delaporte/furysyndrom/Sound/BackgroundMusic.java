package com.delaporte.furysyndrom.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class BackgroundMusic {
    private Music Music;
    private float volume;

    public BackgroundMusic(float volume, String path) {
        this.volume = volume;
        Music = Gdx.audio.newMusic(Gdx.files.internal(path));
        Music.setLooping(true);
        Music.setVolume(volume);
        Music.play();

    }

    public void setVolume(float volume) {
        this.volume = volume;
        Music.setVolume(volume);

    }

    public void stop() {
        Music.stop();
    }

    public float getVolume(){
        return volume;
    }
}
