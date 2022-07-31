/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Label extends GUI {

    public Label(String text, int x, int y) {

        this.labelX = x;
        this.labelY = y;
        this.hasLabel = true;
        this.labelText = text;
        this.doDraw = true;

    }

    public Label(String text, int x, int y, int sx, int sy) {
        this(text, x, y);
        this.x = labelX;
        this.y = labelY;
        this.setSize(sx, sy);
    }

    public void setPos(int x, int y) {
        this.labelX = x;
        this.labelY = y;
        this.x = labelX;
        this.y = labelY;
    }

    public void setBackColor(Color color) {
        this.hasBackground = true;
        this.backColor = color;
    }

    public void setColor(Color color) {
        this.labelColor = color;
    }

    public void setText(String text) {
        this.labelText = text;
    }

    public void setSize(int sx, int sy) {
        this.sx = sx;
        this.sy = sy;
    }

    public String getText() {
        return this.labelText;
    }

    public void setFont(String font_family, int size) {
        this.labelFont = new Font(font_family, Font.BOLD, size);
    }

    public void setFont(Font font) {
        this.labelFont = font;
    }

    public Font getFont() {
        return this.labelFont;
    }

    @Override
    public void update(Window win) {
    }
}
