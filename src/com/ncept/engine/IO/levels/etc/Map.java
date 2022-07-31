/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.IO.levels.etc;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
@XStreamAlias("ncept")
public class Map {

    public String name;
    public String bgm;
    public MapTile[] tiles;
    public MapTileArea[] tilesAreas;
}
