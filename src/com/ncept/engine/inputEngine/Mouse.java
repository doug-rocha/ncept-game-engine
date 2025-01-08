/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.inputEngine;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.utils.Debug;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Mouse extends InputCodes implements MouseListener, MouseMotionListener {

    private ArrayList<Integer> PressedButtons = new ArrayList<>();
    private ArrayList<Integer> DownButtons = new ArrayList<>();

    private static int x, y;

    @Override
    public void mousePressed(MouseEvent e) {
        int btn = e.getButton();
        if (DownButtons.indexOf(btn) == -1) {
            PressedButtons.add(btn);
            DownButtons.add(btn);
            if (EngineProperties.DEBUG_MODE) {
                Debug.LOG("button added " + btn);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        int btn = e.getButton();
        if (PressedButtons.indexOf(btn) != -1) {
            PressedButtons.remove(PressedButtons.indexOf(btn));
        }
        if (DownButtons.indexOf(btn) != -1) {
            DownButtons.remove(DownButtons.indexOf(btn));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    //GAME
    public boolean isBtnPressed(int btn) {
        if (PressedButtons.indexOf(btn) != -1) {
            PressedButtons.remove(PressedButtons.indexOf(btn));
            return true;
        }
        return false;
    }

    public boolean isMouseUp(int btn) {
        return !DownButtons.contains(btn);
    }

    public boolean isBtnDown(int key) {
        return DownButtons.indexOf(key) != -1;
    }

    public int getX() {
        return x - EngineProperties.BUFFER_X;
    }

    public int getY() {
        return y - EngineProperties.BUFFER_Y;
    }

    public Point getCodinates() {
        return new Point(x - EngineProperties.BUFFER_X, y - EngineProperties.BUFFER_Y);
    }

    //UNUSED
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

}
