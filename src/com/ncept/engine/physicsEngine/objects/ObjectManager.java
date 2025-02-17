/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.physicsEngine.objects;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.GameManager;
import com.ncept.engine.renderEngine.core.Window;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class ObjectManager {

    private static ArrayList<GameObject> Objects = new ArrayList<GameObject>();

    /**
     * add an object to game
     *
     * @param go object to be added
     */
    public static void ADD_OBJECT(GameObject go) {
        go.recalculateSize();
        Objects.add(go);
    }

    /**
     * add a List of objects to game
     *
     * @param go
     */
    public static void ADD_OBJECT_LIST(List<GameObject> go) {
        for (GameObject obj : go) {
            obj.recalculateSize();
        }
        Objects.addAll(go);
    }

    /**
     * Remove an object from game
     *
     * @param go objeto a ser removido
     */
    public static void REMOVE_OBJECT(GameObject go) {
        Objects.remove(go);
    }

    /**
     * Clear all the objects
     */
    public static void FLUSH_OBJECTS() {
        Objects.clear();
    }

    /**
     * update all the objects
     *
     * @param win the game Window
     * @param gm the GameManager that manage everything
     */
    public static void UPDATE(Window win, GameManager gm) {
        for (int i = 0; i < Objects.size(); i++) {
            GameObject go = Objects.get(i);
            if (go.isDestroyed) {
                Objects.remove(go);
            } else {
                go.update(win, gm);
            }
        }
    }

    /**
     * render all objcts that need to be rendered
     *
     * @param win the game Window
     * @param d the Drawer where the draw calls are placed
     */
    public static void RENDER(Window win, Drawer d) {
        for (int i = 0; i < Objects.size(); i++) {
            GameObject go = Objects.get(i);
            if (go.isOnScreen(d) || go.isOutBoundRender()) {
                go.render(win, d);
            }
        }
    }

    /**
     * Get all the game objects
     *
     * @return a list of the objects in game
     */
    public static ArrayList<GameObject> GET_OBJECTS() {
        return Objects;
    }

    /**
     * Get an object index
     *
     * @param go the object to be located
     * @return the index of said object on the list
     */
    public static int GET_OBJECT_INDEX(GameObject go) {
        int retorno = -1;
        for (int i = 0; i < Objects.size(); i++) {
            if (Objects.get(i) == go) {
                retorno = i;
            }
        }
        return retorno;
    }

    /**
     * Get an object by it's index
     *
     * @param index the index of the object
     * @return an object corresponding of the index
     */
    public static GameObject GET(int index) {
        return Objects.get(index);
    }

    public static void UPDATE_SIZES() {
        for (GameObject go : Objects) {
            if (!go.isDestroyed) {
                go.recalculateSize();
            }
        }
    }

}
