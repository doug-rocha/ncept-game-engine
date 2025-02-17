/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.events.ModResolListener;
import com.ncept.engine.utils.Debug;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class GraphicsCore {

    public static Graphics2D G;
    public static BufferedImage BUFFER;
    public static double MODSIZE_X, MODSIZE_Y, MOD_RESOL = 1.0;
    static double LAST_MOD_RESOL = 1.0;

    private static List<ModResolListener> listeners = new ArrayList<>();

    public static void calcMods(int frameWidth, int frameHeight) {
        MODSIZE_X = Double.valueOf(frameWidth) / EngineProperties.ORIGINAL_WIDTH;
        MODSIZE_Y = Double.valueOf(frameHeight) / EngineProperties.ORIGINAL_HEIGHT;
        MOD_RESOL = MODSIZE_X < MODSIZE_Y ? MODSIZE_X : MODSIZE_Y;
        if (modHasChanged()) {
            notifyModResolChange();
        }
        Debug.LOG(MOD_RESOL);
    }

    public static synchronized int calcSize(double value) {
        return (int) Math.round(calcSizeDouble(value));
    }

    public static synchronized double calcSizeDouble(double value) {
        return value * MOD_RESOL;
    }

    static synchronized boolean modHasChanged() {
        return LAST_MOD_RESOL != MOD_RESOL;
    }

    public static synchronized double subCalcSizeDouble(double value) {
        return value / MOD_RESOL;
    }

    public static synchronized void addModResolListener(ModResolListener listener) {
        listeners.add(listener);
    }

    private static void notifyModResolChange() {
        listeners.forEach(x -> x.onModResolChange(MOD_RESOL));
    }
}
