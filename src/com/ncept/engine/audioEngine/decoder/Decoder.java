/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.audioEngine.decoder;

import com.ncept.engine.audioEngine.AudioClip;
import com.ncept.engine.utils.Debug;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Decoder implements Runnable {

    private SourceDataLine line;
    private float gain = 0.0f;
    private File arquivo;
    private boolean complete = false;

    private void play()
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        AudioInputStream in = AudioSystem.getAudioInputStream(arquivo);
        AudioFormat baseFormat = in.getFormat();
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);
        AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat, in);
        rawPlay(decodedFormat, din);
        in.close();
    }

    public void setFile(String path) {
        setFile(new File(path));
    }

    public void setFile(File file) {
        arquivo = file;
    }

    public void setAudioClip(AudioClip clip) {
        setFile(clip.getFile());
    }

    private void rawPlay(AudioFormat targetFormat, AudioInputStream din)
            throws IOException, LineUnavailableException {
        byte[] data = new byte[4096];
        line = getLine(targetFormat);
        if (line != null) {
            applyGain();
            line.start();
            int nBytesRead = 0, nBytesWritten = 0;
            while (nBytesRead != -1) {
                nBytesRead = din.read(data, 0, data.length);
                if (nBytesRead != -1) {
                    nBytesWritten = line.write(data, 0, nBytesRead);
                }
            }
            line.drain();
            line.stop();
            line.close();
            din.close();
        }
    }

    private SourceDataLine getLine(AudioFormat audioFormat) throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine res = (SourceDataLine) AudioSystem.getLine(info);
        res.open();
        return res;
    }

    private void applyGain() {
        FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(this.gain);
    }

    public void setGain(float gain, boolean instantApply) {
        setGain(gain);
        if (instantApply) {
            applyGain();
        }
    }

    public void setGain(float gain) {
        this.gain = gain;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public synchronized void run() {
        try {
            this.complete = false;
            play();
            this.complete = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }

    }

    public void stop() {
        if (line != null) {
            line.stop();
            line.close();
        }
    }

}
