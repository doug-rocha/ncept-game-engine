/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.IO.levels.etc;

import com.ncept.engine.physicsEngine.objects.etc.ObjectType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
@XStreamAlias("tile")
public class MapTile {

    public int x, y, sx, sy;
    public String img;
    public ObjectType type;
    public boolean colored;
    public int color_r, color_g, color_b, color_a;
    public int hb_x, hb_y, hb_sx, hb_sy;
    public boolean colision;
}
