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
     * Adiciona um objeto ao jogo
     *
     * @param go objeto a ser adicionado
     */
    public static void ADD_OBJECT(GameObject go) {
        Objects.add(go);
    }

    public static void ADD_OBJECT_LIST(List<GameObject> go) {
        Objects.addAll(go);
    }

    /**
     * Remove um objeto do jogo
     *
     * @param go objeto a ser removido
     */
    public static void REMOVE_OBJECT(GameObject go) {
        Objects.remove(go);
    }

    /**
     * Limpa todos os objetos do jogo
     */
    public static void FLUSH_OBJECTS() {
        Objects.clear();
    }

    public static void UPDATE(Window win, GameManager gm) {
        for (int i = 0; i < Objects.size(); i++) {
            GameObject go = Objects.get(i);
            if (go.isDestroyed) {
                Objects.remove(go);
            } else {
                go.update(win, gm);
                go.recalculateSize();
            }
        }
    }

    public static void RENDER(Window win, Drawer d) {
        for (int i = 0; i < Objects.size(); i++) {
            GameObject go = Objects.get(i);
            go.render(win, d);
        }
    }

    public static ArrayList<GameObject> GET_OBJECTS() {
        return Objects;
    }

    public static int GET_OBJECT_INDEX(GameObject go) {
        int retorno = -1;
        for (int i = 0; i < Objects.size(); i++) {
            if (Objects.get(i) == go) {
                retorno = i;
            }
        }
        return retorno;
    }

    public static GameObject GET(int index) {
        return Objects.get(index);
    }

}
