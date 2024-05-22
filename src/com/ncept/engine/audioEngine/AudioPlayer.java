/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.audioEngine;

import com.ncept.engine.Properties;
import com.ncept.engine.audioEngine.decoder.Decoder;
import com.ncept.engine.utils.Debug;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class AudioPlayer {

    private Decoder dec;
    private Thread decThread;
    private AudioClip audioClip;
    private boolean paused;

    private float volume;

    public AudioPlayer() {
        audioClip = null;
        paused = false;
        dec = new Decoder();
        decThread = new Thread(dec);
        setVolume(Properties.VOLUME);
    }

    public AudioPlayer(AudioClip SFX) {
        this();
        this.setAudioClip(SFX);
        this.setVolume(volume);
    }

    public AudioPlayer(AudioClip SFX, double volume) {
        this();
        this.setAudioClip(SFX);
        this.setVolume(volume);
    }

    public void setAudioClip(AudioClip clip) {
        audioClip = clip;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public void setVolume(double volume) {
        this.volume = (float) volume;
        dec.setGain(convertVolumeToGain(volume));
    }

    public void setVolume(double volume, boolean instantApply) {
        this.volume = (float) volume;
        dec.setGain(convertVolumeToGain(volume), instantApply);
    }

    private float convertVolumeToGain(double volume) {
        if (volume > 1.0) {
            volume = 1.0;
        } else if (volume < 0) {
            volume = 0.0;
        }
        return (float) (Math.log(volume) / Math.log(10) * 20);
    }

    public boolean isComplete() {
        return dec.isComplete();
    }

    public void play() {
        if (this.audioClip != null) {
            dec.setAudioClip(audioClip);
            dec.setGain(convertVolumeToGain(volume));
            decThread = new Thread(dec);
            decThread.start();
        } else {
            if (Properties.DEBUG_MODE) {
                Debug.LOG("Nenhum arquivo selecionado, não vai tocar nada");
            }
        }
    }

    public void play(AudioClip clip) {
        this.setAudioClip(clip);
        play();
    }

    public void stop() {
        if (decThread.isAlive()) {
            dec.stop();
            decThread.interrupt();
        }
    }

    public void pause() {
        if (!this.paused) {
            try {
                decThread.wait();
                this.paused = true;
            } catch (InterruptedException ex) {
                Debug.LOG_ERROR(ex.getMessage());
            }
        } else {
            decThread.notify();
            this.paused = false;
        }
    }

    public boolean isPaused() {
        return paused;
    }
}
