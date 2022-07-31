/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.events;

import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.components.Button;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class ButtonEvent {

    private Button src;
    private Window win;

    public ButtonEvent(Button source) {
        this.src = source;
        this.win = Window.getWindow();

    }

    public Window getWindow() {
        return win;
    }

    public Button getSource() {
        return src;
    }
}
