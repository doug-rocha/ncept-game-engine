/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class GUIManager {

    private static ArrayList<GUI> Objects = new ArrayList<GUI>();

    public static void RENDER(Window win, Drawer d) {
        Point camera = new Point(d.getCX(), d.getCY());
        d.setCameraPos(0, 0);
        for (int i = 0; i < Objects.size(); i++) {
            GUI gui = Objects.get(i);
            gui.render(win, d);
        }
        d.setCameraPos(camera.x, camera.y);
    }

    public static void UPDATE(Window win) {
        for (int i = 0; i < Objects.size(); i++) {
            GUI gui = Objects.get(i);
            if (gui.doDraw) {
                gui.update(win);
            }
        }
    }

    public static void ADD_LIST(ArrayList<GUI> objects) {
        for (GUI gui : objects) {
            gui.recalculateSize();
        }
        Objects.addAll(objects);
    }

    public static void ADD(GUI gui) {
        gui.recalculateSize();
        Objects.add(gui);
    }

    public static void REMOVE(GUI gui) {
        Objects.remove(gui);
    }

    public static void FLUSH() {
        Objects.clear();
    }

    public static GUI GET(int index) {
        return Objects.get(index);
    }

    public static int GET_OBJECT_INDEX(GUI gui) {
        int retorno = -1;
        for (int i = 0; i < Objects.size(); i++) {
            if (Objects.get(i) == gui) {
                retorno = i;
            }
        }
        return retorno;
    }

    public static ArrayList<GUI> GET_OBJECTS() {
        return Objects;
    }

    public static void UPDATE_SIZES() {
        for (GUI gui : Objects) {
            if (gui.doDraw) {
                gui.recalculateSize();
            }
        }
    }

}
