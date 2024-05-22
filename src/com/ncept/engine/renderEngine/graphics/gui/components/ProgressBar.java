/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class ProgressBar extends GUI {

    private double widthMod;

    private boolean rounded;
    private int ru, rd;

    private boolean glow, glowUp;
    private int glowingTime;
    private Color glowingColor;

    public ProgressBar(int x, int y, int sx, int sy) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        showWireFrame = true; //@todo remover -> wireframe
        labelFont = new Font("dialog", Font.BOLD, 14);
        labelColor = Color.WHITE;
        widthMod = 0.0;
        setVisible(true);
    }

    public ProgressBar(int x, int y, int sx, int sy, String label) {
        this(x, y, sx, sy);
        this.setText(label);
    }

    @Override
    public void update(Window win) {
        if (glow) {
            if (glowUp) {
                glowingTime += 12;
            } else {
                glowingTime -= 15;
            }
            if (glowingTime > 255) {
                glowingTime = 255;
                glowUp = false;
            } else if (glowingTime < 0) {
                glowingTime = 0;
                glowUp = true;
            }
        }
    }

    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (glow) {
                for (int i = 0; i <= 5; i++) {
                    glowingColor = new Color(glowingColor.getRed(), glowingColor.getBlue(), glowingColor.getBlue(), glowingTime / (i + 1));
                    if (rounded) {
                        d.drawRoundRect((int) mx - i, (int) my - i, (int) msx + (i * 2), (int) msy + (i * 2), (int) ru, (int) rd, glowingColor);
                    } else {
                        d.drawRect((int) mx - i, (int) my - i, (int) msx + (i * 2), (int) msy + (i * 2), glowingColor);
                    }
                }
            }
            if (rounded) {
                d.fillRoundRect((int) mx, (int) my, (int) msx, (int) msy, ru, rd, frameColor);
                d.fillRoundRect((int) mx, (int) my, (int) (msx * widthMod), (int) sy, ru, rd, backColor);
            } else {
                d.fillRect((int) mx, (int) my, (int) msx, (int) msy, frameColor);
                d.fillRect((int) mx, (int) my, (int) (msx * widthMod), (int) msy, backColor);
            }
            if (hasLabel && !labelText.equals("")) {
                d.drawString(labelText, labelColor, mLabelFont, (int) (mx + (msx / 2) - (d.getFontMetrics(mLabelFont).stringWidth(labelText) / 2)), (int) my - 5);
            }
        }
    }

    @Override
    public void setText(String string) {
        this.labelText = string;
        this.hasLabel = true;
    }

    public void setColor(Color color, Color lblColor) {
        this.backColor = color;
        this.frameColor = new Color(color.getRed() / 3, color.getGreen() / 3, color.getBlue() / 3, color.getAlpha() / 3);
        this.labelColor = lblColor;
    }

    public void setRoundedCorner(int u, int d) {
        this.ru = u;
        this.rd = d;
        this.rounded = true;
    }

    public void setGlowing(boolean glow) {
        this.glow = glow;
        this.glowingTime = 0;
        this.glowingColor = backColor;
        this.glowUp = true;
    }

    public boolean isGlowing() {
        return glow;
    }

    public String getText() {
        return this.labelText;
    }

    public void setValue(double value) {
        this.widthMod = value;
    }

    public double getValue() {
        return widthMod;
    }

    public void setPercent(double value) {
        this.widthMod = value / 100.0;
    }

}
