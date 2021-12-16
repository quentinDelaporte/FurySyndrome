package com.delaporte.furysyndrom.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class AmbiantSound {
    private Music sound;
    private float volume;

    public AmbiantSound(float volume, String path) {
        this.volume = volume;
        sound = Gdx.audio.newMusic(Gdx.files.internal(path));
        sound.setLooping(false);
        sound.setVolume(this.volume);
    }
    
    public void setVolume(float volume) {
        this.volume = volume;
        sound.setVolume(volume);
    }

    public void play(){
        if(sound != null)
            sound.play();
    }

    public float getVolume(){
        return volume;
    }
}
