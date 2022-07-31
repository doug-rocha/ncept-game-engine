/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class GUI {

    protected float x, y, sx, sy;
    protected Image img;
    protected boolean hasImage, hasBackground, doDraw, showWireFrame;
    protected boolean hasLabel;
    protected Color backColor, frameColor, labelColor = Color.WHITE;

    protected String labelText = "";
    protected Font labelFont;
    protected int labelX, labelY;

    public float getSX() {
        return sx;
    }

    public float getSY() {
        return sy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public abstract void update(Window win);

    public void render(Window win, Drawer d) {

        if (doDraw) {
            if (hasBackground) {
                d.fillRect((int) x, (int) y, (int) sx, (int) sy, backColor);
            } else if (hasImage && img != null) {
                d.drawImage(img, (int) x, (int) y);
            }
            if (showWireFrame) {
                d.drawRect((int) x, (int) y, (int) sx, (int) sy, frameColor);
            }
            if (hasLabel && !labelText.equals("")) {
                d.drawString(labelText, labelColor, labelFont, labelX, labelY);
            }
        }

    }

    public void setVisible(boolean visible) {
        doDraw = visible;
    }

    public boolean isVisible() {
        return this.doDraw;
    }

    public abstract void setText(String string);
}
