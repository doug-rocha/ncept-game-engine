/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.etc;

import com.ncept.engine.renderEngine.graphics.gui.GUI;
import com.ncept.engine.renderEngine.graphics.gui.GUIManager;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class GuiBLinker {

    public static void blink(long start_time, long time_passed, long ticks_passed, int steps, int time_steps, GUI gui) {
        if (time_passed > start_time) {
            boolean executed = false;
            for (int i = 1; i <= steps; i++) {
                if (time_passed < (start_time + (time_steps * i)) && ticks_passed % i == 0 && !executed) {
                    blinkToggle(gui);
                }
            }
            if (time_passed > (start_time + ((steps + 1) * time_steps))) {
                GUIManager.REMOVE(gui);
            }
        }
    }

    private static void blinkToggle(GUI gui) {
        gui.setVisible(!gui.isVisible());
    }
}
