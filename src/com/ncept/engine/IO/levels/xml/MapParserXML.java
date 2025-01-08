/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.IO.levels.xml;

import com.ncept.engine.IO.levels.etc.MapTile;
import com.ncept.engine.IO.levels.etc.MapTileArea;
import com.thoughtworks.xstream.XStream;
import java.io.File;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
@Deprecated
public class MapParserXML {

    protected XStream xStream;

    protected Class classToLoad;

    public MapParserXML(Class class_to_load) {
        this.classToLoad = class_to_load;
        xStream = new XStream();
        xStream.alias("ncept", classToLoad);
        xStream.alias("tile", MapTile.class);
        xStream.alias("tilesArea", MapTileArea.class);
    }

    public Object loadMap(String file_path) {
        return xStream.fromXML(file_path);
    }

    public Object loadMap(File file) {
        return xStream.fromXML(file);
    }

    public void addAlias(String alias, Class classe) {
        xStream.alias(alias, classe);
    }
}
