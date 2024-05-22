package com.ncept.engine.audioEngine;

import com.ncept.engine.utils.Debug;
import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class MidiPlayer {

    private Sequencer player;
    private boolean muted;

    public void play(File file, int loop) {
        try {
            player = MidiSystem.getSequencer();
            Sequence track = MidiSystem.getSequence(file);
            player.open();
            player.setSequence(track);
            player.setLoopCount(loop);
            player.start();
        } catch (Exception ex) {
            Debug.LOG_ERROR("Erro ao reproduzir som MIDI " + ex.getLocalizedMessage());

        }
    }

    public void play(String name, int loop) {
        play(new File(name), loop);
    }
    
    public void stop(){
        player.stop();
    }

    public void mute() {
        if (!muted) {
            for (int i = 0; i < 30; i++) {
                player.setTrackMute(i, true);
            }
            muted = true;
        }
    }

    public void unmute() {
        if (muted) {
            for (int i = 0; i < 30; i++) {
                player.setTrackMute(i, false);
            }
            muted = false;
        }
    }

    public boolean isMuted() {
        return muted;
    }

}
