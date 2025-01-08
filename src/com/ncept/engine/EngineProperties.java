/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class EngineProperties {

    public static final String ENGINE_VERSION = "0.3.0-dev";

    public static int WIDTH = 1280, HEIGHT = 720;
    public static int BUFFER_WIDTH = 1280, BUFFER_HEIGHT = 689;
    public static int ORIGINAL_WIDTH = 1280, ORIGINAL_HEIGHT = 720;
    public static double VOLUME = 0.3;
    public static boolean FULL_SCREEN = false, ANTI_ALIASING = true;
    
    public static int BUFFER_X, BUFFER_Y;

    public static double UPDATE_SPEED = 90, FRAMES_PS = 90;

    public static boolean DEBUG_MODE = false;
    
    public static double DELTA_TIME;
    
    public static boolean MOD_CHANGED;
    public static double LAST_MOD;
}
