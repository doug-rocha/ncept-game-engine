/*
 * To change this license header, choose license Header in Project Properties.
 * To chenge this template file, choose Tools | Templates and open in the template editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class MenuItem extends GUI {

    @Override
    public void update(Window win) {
    }

    @Override
    public void setText(String string) {
        if (!string.equals("")) {
            hasLabel = true;
            labelText = string;
        }
    }

    public void setBgColor(Color bgColor) {
        this.backColor = bgColor;
    }

    public void setColor(Color color) {
        this.labelColor = color;
    }

    public void setFont(Font fonte) {
        this.labelFont = fonte;
    }

}
