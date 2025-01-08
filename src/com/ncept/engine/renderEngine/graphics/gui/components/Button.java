/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.audioEngine.AudioClip;
import com.ncept.engine.audioEngine.AudioPlayer;
import com.ncept.engine.events.ButtonEvent;
import com.ncept.engine.events.ButtonListener;
import com.ncept.engine.inputEngine.Input;
import com.ncept.engine.inputEngine.Mouse;
import com.ncept.engine.physicsEngine.collision.Collision;
import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Button extends GUI {

    private Color bgColor = Color.WHITE, bgHoverColor = Color.WHITE, bgClickedColor = Color.WHITE;
    private Color borderColor = Color.GRAY, borderHoverColor = Color.GRAY, borderClickedColor = Color.LIGHT_GRAY;
    private Color textColor = Color.BLACK, textHoverColor = Color.GRAY, textClickedColor = Color.LIGHT_GRAY;

    private AudioClip clickFX, scrollFX;
    private boolean clicked, hover;

    private boolean rounded;
    private int borderRadiusH, borderRadiusV;

    private ButtonListener listener;
    private ButtonEvent event;

    public Button(String title, int x, int y, int sx, int sy, boolean bordered) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.hasBackground = true;
        this.backColor = Color.WHITE;
        this.showWireFrame = bordered;
        this.frameColor = Color.GRAY;

        this.hasLabel = true;
        this.labelFont = new Font("Arial", 1, 16);
        this.labelText = title;
        this.labelColor = Color.BLACK;
        centerText();

        this.doDraw = true;
    }

    public void addButtonListener(ButtonListener listener) {
        this.listener = listener;
        this.event = new ButtonEvent(this);
    }

    public void setRoundedBorder(int rh, int rv) {
        rounded = false;
        if (rh > 0) {
            borderRadiusH = rh;
            rounded = true;
        }
        if (rv > 0) {
            borderRadiusV = rv;
            rounded = true;
        }

    }

    public void setBgColors(Color default_color, Color hover, Color clicked) {
        bgHoverColor = hover;
        bgClickedColor = clicked;
        backColor = bgColor = default_color;
    }

    public void setTextColors(Color default_color, Color hover, Color clicked) {
        textHoverColor = hover;
        textClickedColor = clicked;
        labelColor = textColor = default_color;
    }

    public void setBorderColors(Color default_color, Color hover, Color clicked) {
        borderHoverColor = hover;
        borderClickedColor = clicked;
        frameColor = borderColor = default_color;
    }

    public void setFont(String font, int size) {
        labelFont = new Font(font, Font.BOLD, size);
        centerText();
    }

    public void setSounds(AudioClip click, AudioClip scroll) {
        this.clickFX = click;
        this.scrollFX = scroll;
    }

    @Override
    public void update(Window win) {
        Mouse m = Window.getMouse();

        if (Collision.isMouseColliding(this)) {
            if (m.isBtnPressed(Input.MOUSE_LEFT)) {
                clicked = true;
                this.labelColor = textClickedColor;
                this.frameColor = borderClickedColor;
                this.backColor = bgClickedColor;
                if (clickFX != null) {
                    new AudioPlayer(clickFX, EngineProperties.VOLUME).play();
                }

            } else if (clicked && m.isMouseUp(Input.MOUSE_LEFT)) {
                clicked = false;
                hover = true;
                this.labelColor = textHoverColor;
                this.frameColor = borderHoverColor;
                this.backColor = bgHoverColor;
                if (listener != null) {
                    listener.action(event);
                }
            } else if (!clicked && !hover) {
                hover = true;
                this.labelColor = textHoverColor;
                this.frameColor = borderHoverColor;
                this.backColor = bgHoverColor;
                if (scrollFX != null) {
                    new AudioPlayer(scrollFX, EngineProperties.VOLUME).play();
                }
            }
        } else if (hover) {
            hover = false;
            labelColor = textColor;
            this.frameColor = borderColor;
            this.backColor = bgColor;
        }
    }

    @Override
    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (!rounded) {
                super.render(win, d);
            } else {
                if (hasBackground) {
                    d.fillRoundRect((int) mx, (int) my, (int) msx, (int) msy, borderRadiusH, borderRadiusV, backColor);
                } else if (hasImage && img != null) {
                    d.drawImage(img, (int) mx, (int) my);
                }
                if (showWireFrame) {
                    d.drawRoundRect((int) mx, (int) my, (int) msx, (int) msy, borderRadiusH, borderRadiusV, frameColor);
                }
                if (hasLabel && !labelText.equals("")) {
                    d.drawString(labelText, labelColor, mLabelFont, mLabelX, mLabelY);
                }
            }
        }
    }

    private void centerText() {
        FontMetrics fm = Window.getWindow().getFontMetrics(labelFont);
        this.labelX = (int) (x + (sx / 2) - (fm.stringWidth(labelText) / 2));
        this.labelY = (int) (y + (sy / 2) + (fm.getDescent()));
    }

    @Override
    public void setText(String string) {
        this.labelText = string;
    }
}
