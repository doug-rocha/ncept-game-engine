package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class ProgressText extends GUI {

    private LinearGradientPaint paint;
    private double widthMod;

    private boolean glow, glowUp;
    private int glowingTime;
    private Color glowingColor;

    public ProgressText(String text, int x, int y, Font font, Color progress, Color todo) {
        this.x = x;
        this.y = y;
        this.labelFont = font;
        widthMod = 0.000001f;
        this.setText(text);
        this.setVisible(true);
        this.calcSizes();
        this.setPaint(progress, todo);
        this.recalculateGradient();
    }

    @Override
    public void update(Window win) {
        if (glow) {
            if (glowUp) {
                glowingTime += 5;
            } else {
                glowingTime -= 7;
            }
            if (glowingTime > 255) {
                glowingTime = 255;
                glowUp = false;
            } else if (glowingTime < 0) {
                glowingTime = 0;
                glowUp = true;
            }
        }
        recalculateGradient();
    }

    @Override
    public void render(Window win, Drawer d) {

        if (doDraw) {
            if (glow) {
                for (int i = 0; i <= 5; i++) {
                    glowingColor = new Color(glowingColor.getRed(), glowingColor.getGreen(), glowingColor.getBlue(), glowingTime / (i + 1));
                    d.drawString(labelText, glowingColor, labelFont.deriveFont(labelFont.getStyle(), labelFont.getSize() + i), (int) mx - (i * 2), (int) my);
                }
            }
            d.drawString(labelText, paint, labelFont, (int) mx, (int) my);
        }

    }

    @Override
    public void setText(String text) {
        this.labelText = text;
    }

    public String getText() {
        return labelText;
    }

    public void setValue(double value) {
        this.widthMod = value;
        if (widthMod == 1f) {
            backColor = labelColor;
        } else if (widthMod >= 0.9f) {
            int[] difs = {
                labelColor.getRed() - backColor.getRed(),
                labelColor.getGreen() - backColor.getGreen(),
                labelColor.getBlue() - backColor.getBlue(),
                labelColor.getAlpha() - backColor.getAlpha()
            };
            backColor = new Color(backColor.getRed() + (difs[0] / 2), backColor.getGreen() + (difs[1] / 2), backColor.getBlue() + (difs[2] / 2), backColor.getAlpha() + (difs[3] / 2));
        }
        if (glow) {
            glowingColor = backColor;
        }
        recalculateGradient();
    }

    public double getValue() {
        return widthMod;
    }

    public void setPercent(double value) {
        setValue(value / 100.0);
    }

    public double getPercent() {
        return getValue() * 100.0;
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

    public Font getLabelFont() {
        return labelFont;
    }

    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }

    public void setPaint(Color progress, Color todo) {
        this.labelColor = progress;
        this.backColor = todo;
        recalculateGradient();
    }

    private void recalculateGradient() {
        paint = new LinearGradientPaint((float) mx, (float) my, (float) (mx + sx), (float) my, new float[]{.0f, (float) widthMod}, new Color[]{labelColor, backColor});
    }

    private void calcSizes() {
        this.sx = Window.getDrawer().getFontMetrics(labelFont).stringWidth(labelText);
        this.sy = Window.getDrawer().getFontMetrics(labelFont).getHeight();
    }

}
