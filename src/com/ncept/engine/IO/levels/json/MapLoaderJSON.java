package com.ncept.engine.IO.levels.json;

import com.ncept.engine.IO.levels.MapLoader;
import com.ncept.engine.IO.levels.etc.Map;
import com.ncept.engine.IO.levels.etc.MapTile;
import com.ncept.engine.IO.levels.etc.MapTileArea;
import com.ncept.engine.IO.levels.etc.Prefab;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
@Deprecated
public abstract class MapLoaderJSON extends MapLoader {

    protected MapParserJSON mapParser;

    public MapLoaderJSON(String filePath) {
        super(filePath);
        mapParser = new MapParserJSON(Map.class);
    }

    public MapLoaderJSON(String filePath, Class mapClass) {
        super(filePath);
        this.mapClass = mapClass;
        mapParser = new MapParserJSON(mapClass);
    }

    @Override
    public String getMapName() {
        String retorno;
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
    public void loadMap() throws IOException {
        mapa = mapParser.loadMap(filePath);
        loadPrefabs();
        createObjects();
        loaded = true;
    }

    @Override
    public void loadPrefabs() throws IOException {
        if (((Map) mapa).prefabs != null) {
            List<MapTile> tileList = new ArrayList();
            if (((Map) mapa).tiles != null) {
                tileList.addAll(Arrays.asList(((Map) mapa).tiles));
            }
            for (Prefab pre : ((Map) mapa).prefabs) {
                Object aux = mapParser.loadMap(prefabsFolder + pre.prefabName);
                tileList.addAll(createPrefabTiles(pre, aux));
            }
            ((Map) mapa).tiles = tileList.toArray(new MapTile[0]);
        }
    }

    @Override
    public List<MapTile> createPrefabTiles(Prefab prefab, Object map) {
        List<MapTile> prefabTiles = new ArrayList();
        if (((Map) map).tiles != null) {
            for (MapTile tile : ((Map) map).tiles) {
                tile.x += prefab.x;
                tile.y += prefab.y;
                tile.zIndex += prefab.zIndex;
                prefabTiles.add(tile);
            }
        }
        if (((Map) map).tilesAreas != null) {
            for (MapTileArea tileArea : ((Map) map).tilesAreas) {
                tileArea.x += prefab.x;
                tileArea.y += prefab.y;
                tileArea.zIndex += prefab.zIndex;
                prefabTiles.addAll(Arrays.asList(tileArea.getMapTiles()));
            }
        }
        return prefabTiles;
    }

}
