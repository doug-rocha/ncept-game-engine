/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.physicsEngine.objects.GameObject;
import com.ncept.engine.physicsEngine.objects.ObjectManager;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import com.ncept.engine.renderEngine.graphics.gui.GUIManager;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class GameLevel {

    protected int cameraX, cameraY;

    protected double playerX, playerY;

    protected boolean running, updated, rendered;

    protected boolean finished;

    protected Integer nxtLevelId;

    protected GameLevel nxtLevel;

    protected ArrayList<GUI> GUI = new ArrayList<>();
    protected ArrayList<GameObject> GO = new ArrayList<>();

    public GameLevel(int level_id) {
        this.levelID = level_id;
    }

    protected int levelID;

    public abstract void init(Window win, GameManager manager);

    public abstract void render(Window win, Drawer d, GameManager manager);

    public abstract void update(Window win, GameManager manager);

    public int getLevel() {
        return levelID;
    }

    protected void flushAll() {
        GUIManager.FLUSH();
        ObjectManager.FLUSH_OBJECTS();
    }

    public boolean isFinished() {
        return finished;
    }

    public Integer getNxtLevelId() {
        return nxtLevelId;
    }

    public GameLevel getNxtLevel() {
        return nxtLevel;
    }

}
