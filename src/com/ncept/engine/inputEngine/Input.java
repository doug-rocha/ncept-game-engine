/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.inputEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Input extends InputCodes implements KeyListener {

    private ArrayList<Integer> PressedKeys = new ArrayList<>();
    private ArrayList<Integer> DownKeys = new ArrayList<>();

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (DownKeys.indexOf(key) == -1) {
            PressedKeys.add(key);
            DownKeys.add(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (PressedKeys.indexOf(key) != -1) {
            PressedKeys.remove(PressedKeys.indexOf(key));
        }
        if (DownKeys.indexOf(key) != -1) {
            DownKeys.remove(DownKeys.indexOf(key));
        }
    }

    //GAME ACCESS
    public boolean iskeyPressed(int key) {
        if (PressedKeys.indexOf(key) != -1) {
            PressedKeys.remove(PressedKeys.indexOf(key));
            return true;
        }
        return false;
    }

    public boolean isKeyDown(int key) {
        return DownKeys.indexOf(key) != -1;
    }

    //UNUSED METHODS
    @Override
    public void keyTyped(KeyEvent ke) {
    }

}
