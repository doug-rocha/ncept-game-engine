/*
 * To change this license header, choose license Header in Project Properties.
 * To chenge this template file, choose Tools | Templates and open in the template editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class MenuPanel extends GUI {

    private ArrayList<MenuItem> itemList = new ArrayList<>();
    private ArrayList<MenuItem> activeItemList = new ArrayList<>();

    @Override
    public void update(Window win) {
        activeItemList.clear();
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).isVisible()) {
                activeItemList.add(itemList.get(i));
            }
        }
    }

    @Override
    public void setText(String string) {
    }
    
    @Override
    public void render(Window win, Drawer d){
        for (int i=0;i<activeItemList.size();i++){
            activeItemList.get(i).render(win, d);
        }
    }

}
