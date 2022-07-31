/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.audioEngine;

import com.ncept.engine.utils.Debug;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class AudioClip {

    private File AudioFile;

    public AudioClip(String path) {
        AudioFile = new File(path);
        if (!AudioFile.exists()) {
            Debug.LOG_ERROR("ERRO >> O clipe de áudio " + path + " não foi encontrado");
        }
    }

    public File getFile() {
        return AudioFile;
    }

    public AudioInputStream getAudioStream() {
        try {
            return AudioSystem.getAudioInputStream(AudioFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
