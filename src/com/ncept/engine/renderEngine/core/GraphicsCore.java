/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.Properties;
import com.ncept.engine.utils.Debug;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class GraphicsCore {

    public static Graphics2D G;
    public static BufferedImage BUFFER;
    public static double MODSIZE_X, MODSIZE_Y, MOD_RESOL = 1.0;
    static double LAST_MOD_RESOL = 1.0;

    public static void calcMods(int frameWidth, int frameHeight) {
        MODSIZE_X = Double.valueOf(frameWidth) / Properties.ORIGINAL_WIDTH;
        MODSIZE_Y = Double.valueOf(frameHeight) / Properties.ORIGINAL_HEIGHT;
        MOD_RESOL = MODSIZE_X < MODSIZE_Y ? MODSIZE_X : MODSIZE_Y;
        Debug.LOG(MOD_RESOL);
    }

    public static int calcSize(double value) {
        return (int) Math.round(calcSizeDouble(value));
    }

    public static double calcSizeDouble(double value) {
        return value * MOD_RESOL;
    }

    static boolean modHasChanged() {
        return LAST_MOD_RESOL != MOD_RESOL;
    }
}
