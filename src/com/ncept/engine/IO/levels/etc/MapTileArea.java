/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.IO.levels.etc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
@XStreamAlias("tileArea")
public class MapTileArea extends MapTile {

    public int quantX, quantY;
    public boolean random;

    public MapTile[] getMapTiles(double mod_resol) {
        sx = (int) (sx * mod_resol);
        sy = (int) (sy * mod_resol);
        ArrayList<MapTile> tiles = new ArrayList<>();
        if (!random) {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    MapTile tile = new MapTile();
                    tile.x = x + (sx * posX);
                    tile.y = y + (sy * posY);
                    tile.sx = sx;
                    tile.sy = sy;
                    tile.img = img;
                    tile.type = type;
                    tile.colored = colored;
                    tile.color_r = color_r;
                    tile.color_g = color_g;
                    tile.color_b = color_b;
                    tile.color_a = color_a;
                    tile.hb_x = hb_x;
                    tile.hb_y = hb_y;
                    tile.hb_sx = hb_sx;
                    tile.hb_sy = hb_sy;
                    tile.colision = colision;
                    tiles.add(tile);
                }
            }
        } else {
            for (int posX = 0; posX < quantX; posX++) {
                for (int posY = 0; posY < quantY; posY++) {
                    MapTile tile = new MapTile();
                    tile.x = x + (sx * posX) + (int) (Math.random() * sx);
                    tile.y = y + (sy * posY) + (int) (Math.random() * sy);
                    tile.sx = sx;
                    tile.sy = sy;
                    tile.img = img;
                    tile.type = type;
                    tile.colored = colored;
                    tile.color_r = color_r;
                    tile.color_g = color_g;
                    tile.color_b = color_b;
                    tile.color_a = color_a;
                    tile.hb_x = hb_x;
                    tile.hb_y = hb_y;
                    tile.hb_sx = hb_sx;
                    tile.hb_sy = hb_sy;
                    tile.colision = colision;
                    tiles.add(tile);
                }
            }
        }
        MapTile[] Tile = tiles.toArray(new MapTile[tiles.size()]);
        return Tile;
    }

    public MapTile[] getMapTiles() {
        return getMapTiles(1.0);
    }
}
