/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.audioEngine;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javazoom.jl.converter.Converter;

/**
 *  We have a fully support for MP3 files (thanks to jlayer), if you want mpeg audio support use AudioPlayer.
 *  NOTE: in order to have a functional mp3 support you need to add tritonus, mp3spi, and jlayer to your library classpath, and at they need to be above other librarys, in the exactly order showed here
 *  All the components use AudioPlayer, audio player support both mpeg and wav audio files, unless you're facing problems please don't use this.
 *  If you need to go with WAVAudioPlayer for incompatibility reasons, you will need to Override all the engine components that can play sound effects.
 * 
 * @author Douglas Rocha de Oliveira - NonaCept
 */
@Deprecated
public class WAVAudioPlayer {

    public static synchronized void playSound(AudioClip sfx, double vol) {
        Thread thread = new Thread() {
            public void run() {
                try {

                    AudioInputStream stream = sfx.getAudioStream();

                    Clip clip = AudioSystem.getClip();

                    clip.open(stream);
                    setVol(vol, clip);
                    clip.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public static AudioClip convertToWAV(AudioClip mp3_clip) {
        try {
            File file = mp3_clip.getFile();
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

            Converter c = new Converter();
            c.convert(file.getAbsolutePath(), file.getParent() + "/" + fileName + ".wav");

            File newFile = new File(file.getParent() + "/" + fileName + ".wav");

            return new AudioClip(newFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setVol(double vol, Clip clip) {
        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(vol) / Math.log(10) * 20);
        gain.setValue(dB);
    }

}
