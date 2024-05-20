/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.Properties;
import com.ncept.engine.physicsEngine.objects.ObjectManager;
import com.ncept.engine.renderEngine.graphics.gui.GUIManager;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class GameManager {

    private ArrayList<GameLevel> levels = new ArrayList<>();
    private ArrayList<GameLevel> sceneLevels = new ArrayList<>();
    private GameLevel currentLevel;
    private Window win;

    //GAME ACCESS
    public void addLevel(GameLevel level) {
        levels.add(level);
    }

    public void addSceneLevel(GameLevel level) {
        sceneLevels.add(level);
    }

    public void flushLevels() {
        levels.clear();
    }

    public void flushSceneLevels() {
        sceneLevels.clear();
    }

    public void flushAllLevels() {
        flushLevels();
        flushSceneLevels();
    }

    public void removeLevel(GameLevel level) {
        levels.remove(level);
    }

    public void removeLevel(int level_id) {
        levels.remove(level_id);
    }

    public void removeSceneLevel(GameLevel level) {
        sceneLevels.remove(level);
    }

    public void removeSceneLevel(int level_id) {
        sceneLevels.remove(level_id);
    }

    public void enterLevel(int level_id, boolean do_init) {
        currentLevel = levels.get(level_id);
        if (do_init) {
            currentLevel.init(win, this);
        }
    }

    public void enterSceneLevel(int level_id, boolean do_init) {
        currentLevel = sceneLevels.get(level_id);
        if (do_init) {
            currentLevel.init(win, this);
        }
    }

    public void enterLevel(GameLevel level, boolean do_init) {
        currentLevel = level;
        if (do_init) {
            currentLevel.init(win, this);
        }
    }

    public Window createWindow(String title, int width, int height, int buffer_size) {
        win = new Window(title, width, height, buffer_size, this);
        return win;
    }

    public Window createWindow(String title, int width, int height, int buffer_size, boolean undecorated) {
        win = new Window(title, width, height, buffer_size, undecorated, this);
        return win;
    }

    public Window createWindow(String title, int buffer_size) {
        win = new Window(title, buffer_size, this);
        return win;
    }

    //WINDOW ACCESS
    void init() {
        if (isLevelOpen()) {
            currentLevel.init(win, this);
        }
    }

    void render() {
        if (isLevelOpen()) {
            win.clear(Color.black);
            currentLevel.render(win, Window.getDrawer(), this);
            ObjectManager.RENDER(win, Window.getDrawer());
            GUIManager.RENDER(win, Window.getDrawer());
            win.update();
            win.frames++;
        }
    }

    void update() {
        if (isLevelOpen()) {
            currentLevel.update(win, this);
            ObjectManager.UPDATE(win, this);
            GUIManager.UPDATE(win);
            Properties.MOD_CHANGED = GraphicsCore.modHasChanged();
            GraphicsCore.LAST_MOD_RESOL = GraphicsCore.MOD_RESOL;
            win.ticks++;
            if (currentLevel.isFinished()) {
                enterLevel(currentLevel.getNxtLevel(), true);
            }
        }
    }

    private boolean isLevelOpen() {
        return currentLevel != null;
    }

}
