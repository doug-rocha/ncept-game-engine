/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.GraphicsCore;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class GUI {
    
    protected double x, y, sx, sy;
    protected Image img;
    protected boolean hasImage, hasBackground, doDraw, showWireFrame;
    protected boolean hasLabel;
    protected Color backColor, frameColor, labelColor = Color.WHITE;
    
    protected String labelText = "";
    protected Font labelFont;
    protected int labelX, labelY;
    
    private double lastMod;
    private double mx, my, msx, msy;
    private int mLabelX, mLabelY;
    private Font mLabelFont;
    
    public double getSX() {
        return sx;
    }
    
    public double getSY() {
        return sy;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSize(double sx, double sy) {
        this.sx = sx;
        this.sy = sy;
    }
    
    public abstract void update(Window win);
    
    public void render(Window win, Drawer d) {
        
        if (doDraw) {
            if (hasBackground) {
                d.fillRect((int) mx, (int) my, (int) msx, (int) msy, backColor);
            } else if (hasImage && img != null) {
                d.drawImage(img, (int) mx, (int) my);
            }
            if (showWireFrame) {
                d.drawRect((int) mx, (int) my, (int) msx, (int) msy, frameColor);
            }
            if (hasLabel && !labelText.equals("")) {
                d.drawString(labelText, labelColor, mLabelFont, mLabelX, mLabelY);
            }
        }
        
    }
    
    public void setVisible(boolean visible) {
        doDraw = visible;
    }
    
    public boolean isVisible() {
        return this.doDraw;
    }
    
    public void recalculateSize() {
        if (GraphicsCore.MOD_RESOL != lastMod) {
            msx = calcSize(sx);
            msy = calcSize(sy);
            mLabelFont = labelFont.deriveFont(labelFont.getStyle(), calcSize(labelFont.getSize()));
        }
        mx = calcSize(x);
        my = calcSize(y);
        mLabelX = calcSize(labelX);
        mLabelY = calcSize(labelY);
        lastMod = GraphicsCore.MOD_RESOL;
    }
    
    protected int calcSize(double value) {
        return GraphicsCore.calcSize(value);
    }
    
    public abstract void setText(String string);
}
