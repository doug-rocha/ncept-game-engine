package com.ncept.engine.IO.levels;

import com.ncept.engine.IO.levels.etc.MapTile;
import com.ncept.engine.IO.levels.etc.Prefab;
import com.ncept.engine.physicsEngine.objects.GameObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public abstract class MapLoader {

    protected Object mapa;
    protected boolean loaded;
    protected String filePath;
    protected Class mapClass;
    protected String prefabsFolder = "res/levels/prefabs/";

    protected List<GameObject> gObjects;

    public MapLoader(String filePath) {
        loaded = false;
        this.filePath = filePath;
        gObjects = new ArrayList<>();
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

    protected abstract void createObjects();

    public abstract String getMapName();

    public abstract String getBGMName();

    public abstract void loadMap() throws IOException;

    public abstract void loadPrefabs() throws IOException;

    public abstract List<MapTile> createPrefabTiles(Prefab prefab, Object map);
}
