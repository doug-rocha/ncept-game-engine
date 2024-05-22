/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import com.ncept.engine.renderEngine.graphics.imaging.Image;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class ScaledImagePanel extends GUI {

    private float realX, realY;
    private float realSX, realSY;
    private boolean resized;

    public ScaledImagePanel(Image img, int x, int y, int sx, int sy) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        resized = true;
        doDraw = true;
        hasImage = true;
    }

    @Override
    public void update(Window win) {
        imageScale();
    }

    @Override
    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (hasImage && img != null) {
                d.drawImage(img, (int) realX, (int) realY, (int) realSX, (int) realSY);
            }
            if (showWireFrame) {
                d.drawRect((int) x, (int) y, (int) sx, (int) sy, frameColor);
            }
        }
    }

    private void imageScale() {
        if (resized && img != null) {
            resized = false;
            int width = img.getWidth();
            int height = img.getHeight();

            double scaleX = sx / width;
            double scaleY = sy / height;

            double finalScale = Math.min(scaleY, scaleX);

            realSX = (float) (width * finalScale);
            realSY = (float) (height * finalScale);
            realX = (float) (x + (sx / 2) - (realSX / 2));
            realY = (float) (y + (sy / 2) - (realSY / 2));
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        resized = true;
    }

    public void setSize(int sx, int sy) {
        this.sx = sx;
        this.sy = sy;
        resized = true;
    }

    @Override
    public void setText(String string) {
    }

    public void setImage(Image image) {
        img = image;
        resized = true;
        imageScale();
    }

}
