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
@Deprecated
public abstract class XMLMapLoader extends MapLoader {

    protected XMLMapParser mapParser;

    public XMLMapLoader(String filePath) {
        super(filePath);
        mapParser = new XMLMapParser(Map.class);
    }

    public XMLMapLoader(String filePath, Class mapClass) {
        super(filePath);
        this.mapClass = mapClass;
        mapParser = new XMLMapParser(this.mapClass);
    }

    @Override
    public String getMapName() {
        String retorno = "";
        if (loaded) {
            retorno = ((Map) mapa).name;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }

    @Override
    public String getBGMName() {
        String retorno = "";
        if (loaded) {
            retorno = ((Map) mapa).bgm;
        } else {
            throw new IllegalStateException("Wait for the load to end");
        }
        return retorno;
    }

    @Override
    public void loadMap() {
        mapa = mapClass.cast(mapParser.loadMap(new File(filePath)));
        createObjects();
        loaded = true;
    }

    public void addAlias(String alias, Class classe) {
        mapParser.addAlias(alias, classe);
    }
}
