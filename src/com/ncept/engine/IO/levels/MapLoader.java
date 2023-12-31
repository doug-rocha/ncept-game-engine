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
    protected Object mapa;
    protected boolean loaded;
    protected Class mapClass;

    public MapLoader(String file_path) {
        loaded = false;
        this.filePath = file_path;
        mapParser = new XMLMapParser(Map.class);
    }

    public MapLoader(String file_path, Class mapClass) {
        loaded = false;
        this.filePath = file_path;
        this.mapClass = mapClass;
        mapParser = new XMLMapParser(this.mapClass);
    }

    public Class getMapClass() {
        return mapClass;
    }

    public void setMapClass(Class mapClass) {
        this.mapClass = mapClass;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getMapName() {
        String retorno = "";
        if (loaded) {
            retorno = ((Map)mapa).name;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }

    public String getBGMName() {
        String retorno = "";
        if (loaded) {
            retorno = ((Map)mapa).bgm;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }

    public void loadMap() {
        mapa = mapClass.cast(mapParser.loadMap(new File(filePath)));
        
        createObjects();
        loaded = true;
    }

    public void addAlias(String alias, Class classe) {
        mapParser.addAlias(alias, classe);
    }

    protected abstract void createObjects();
}
