/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.IO.levels;

import com.ncept.engine.IO.levels.etc.Map;
import java.io.File;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class MapLoader {

    protected XMLMapParser mapParser;
    protected String filePath;
    protected Map mapa;
    protected boolean loaded;

    public MapLoader(String file_path) {
        loaded = false;
        this.filePath = file_path;
        mapParser = new XMLMapParser(Map.class);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getMapName() {
        String retorno = "";
        if (loaded) {
            retorno = mapa.name;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }
    
    public String getBGMName(){
        String retorno="";
        if (loaded) {
            retorno = mapa.bgm;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }

    public void loadMap() {
        mapa = (Map) mapParser.loadMap(new File(filePath));
        createObjects();
        loaded = true;
    }

    public void addAlias(String alias, Class classe) {
        mapParser.addAlias(alias, classe);
    }

    protected abstract void createObjects();
}
