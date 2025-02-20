package com.ncept.engine.io.levels;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.physicsEngine.objects.GameObject;
import com.ncept.engine.physicsEngine.objects.GameObjectAir;
import com.ncept.engine.utils.Debug;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public abstract class MapLoader {

    protected boolean loaded;
    protected String prefabsFolder = "res/levels/prefabs/";

    protected Properties props;

    protected File mapFile;
    protected File tileFile;

    protected Integer tileSize;

    protected Map<String, File> prefabs = new HashMap<>();

    protected Map<String, Class<? extends GameObject>> tileInfo = new HashMap<>();

    protected List<GameObject> backgroundObjects = new ArrayList<>();

    protected List<GameObject> foregroundObjects = new ArrayList<>();

    protected List<GameObject> gObjects = new ArrayList();

    protected double completion = 1;

    public MapLoader(String filePath) {
        loaded = false;
        this.mapFile = new File(filePath);
        tileInfo.put("0", GameObjectAir.class);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public String getMapName() {
        if (props == null) {
            throw new IllegalStateException("Not loaded yet!");
        }
        return props.getProperty("name");
    }

    public File getMapFile() {
        return mapFile;
    }

    public File getTileFile() {
        return tileFile;
    }

    public Map<String, File> getPrefabs() {
        return prefabs;
    }

    public Map<String, Class<? extends GameObject>> getObjectsClasses() {
        return tileInfo;
    }

    public void putObjectClass(String key, Class<? extends GameObject> objectClass) {
        tileInfo.put(key, objectClass);
    }

    public void removeObjectClass(String key) {
        tileInfo.remove(key);
    }

    public String getBGMName() {
        if (props == null) {
            throw new IllegalStateException("Not loaded yet!");
        }
        return props.getProperty("bgm", null);
    }

    public List<GameObject> getgObjects() {
        if (this.loaded) {
            return gObjects;
        }
        throw new IllegalStateException("Not loaded yet! Trying to get GameObjects before loading.");
    }

    public void loadMap() throws IOException {
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Starting the map loading for " + mapFile.getName());
        }
        completion = 1;
        loadProperties();
        completion = 10;
        loadTileFile();
        completion = 85;
        createObjects();
        completion = 100;
        loaded = true;
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Load finished!");
        }
    }

    protected abstract void loadProperties() throws IOException;

    protected void applyProperties() {
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Map properties loaded");
        }
        tileFile = new File(mapFile.getParent() + "/" + (props.getProperty("file").endsWith(".tiles") ? props.getProperty("file") : props.getProperty("file") + ".tiles"));
        String tileSizeStr = props.getProperty("tileSize", "32");
        tileSize = Integer.valueOf(tileSizeStr);
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Map properties applied");
        }
    }

    protected void loadTileFile() throws IOException {
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Loading tiles for " + tileFile.getName());
        }
        BufferedReader br = new BufferedReader(new FileReader(tileFile));
        long lines = br.lines().count();
        br.close();
        br = new BufferedReader(new FileReader(tileFile));
        String line;
        int mx, my = 0;
        completion = 15;
        double progress = (80.0 * 100.0) / (double) lines;
        while ((line = br.readLine()) != null) {
            String[] lineArr = line.trim().split(" ");
            mx = 0;
            for (String objDesc : lineArr) {
                if (objDesc.startsWith("f")) {
                    String objDescF = objDesc.split(":")[1];
                    createForegroundObject(objDescF, mx * tileSize, my * tileSize);
                } else if (objDesc.startsWith("pf")) {
                    String objDescPref = objDesc.split(":")[1];
                    prefabParser(objDescPref, mx, my);
                    mx--;
                } else {
                    createBackgroundObject(objDesc, mx * tileSize, my * tileSize);
                }
                mx++;
            }
            my++;
            if (completion < 95 && my % 100 == 0) {
                completion += progress;
            }
        }
        br.close();
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Tiles loaded!");
        }
    }

    protected void createObjects() {
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Organizing object collections");
        }
        Collections.sort(backgroundObjects);
        Collections.sort(foregroundObjects);
        gObjects.addAll(backgroundObjects);
        gObjects.addAll(foregroundObjects);
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Organized");
        }
    }

    protected void createForegroundObject(String objDesc, int x, int y) {
        if (!tileInfo.containsKey(objDesc)) {
            Debug.LOG("Tile type " + objDesc + " not found, at " + x + ", " + y + ", ignoring it");
            objDesc = "0";
        }
        try {
            GameObject obj = tileInfo.get(objDesc).newInstance();
            obj.setSizes(tileSize, tileSize);
            obj.setPos(x, y);
            obj.setHitbox(0, 0, tileSize, tileSize);
            obj.setzIndex(1);
            foregroundObjects.add(obj);

        } catch (InstantiationException | IllegalAccessException ex) {
            Debug.LOG_ERROR(ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void createBackgroundObject(String objDesc, int x, int y) {
        if (!tileInfo.containsKey(objDesc)) {
            Debug.LOG("Tile type " + objDesc + " not found, at " + x + ", " + y + ", ignoring it");
            objDesc = "0";
        }
        try {
            GameObject obj = tileInfo.get(objDesc).newInstance();
            obj.setSizes(tileSize, tileSize);
            obj.setPos(x, y);
            obj.setzIndex(0);
            backgroundObjects.add(obj);

        } catch (InstantiationException | IllegalAccessException ex) {
            Debug.LOG_ERROR(ex.getMessage());
            ex.printStackTrace();
        }
    }

    protected void prefabParser(String objDesc, int mx, int my) throws IOException {
        int oldTileSize = tileSize;
        mx *= oldTileSize;
        my *= oldTileSize;
        File prefab = prefabs.get(objDesc);
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Parsing properties for prefab " + prefab.getName());
        }
        Properties propsPrefab = new Properties();
        propsPrefab.load(new FileInputStream(prefab));
        File prefabTile = new File(prefab.getParent() + "/"
                + (propsPrefab.getProperty("file").endsWith(".tiles") ? propsPrefab.getProperty("file") : propsPrefab.getProperty("file") + ".tiles"));
        tileSize = Integer.valueOf(propsPrefab.getProperty("tileSize"));

        BufferedReader br = new BufferedReader(new FileReader(prefabTile));
        String line;
        int mpx, mpy = 0;
        while ((line = br.readLine()) != null) {
            String[] lineArr = line.trim().split(" ");
            mpx = 0;
            for (String objDescT : lineArr) {
                if (objDescT.startsWith("f")) {
                    String objDescF = objDescT.split(":")[1];
                    createForegroundObject(objDescF, mx + mpx * tileSize, my + mpy * tileSize);
                } else {
                    createBackgroundObject(objDescT, mx + mpx * tileSize, my + mpy * tileSize);
                }
                mpx++;
            }
            mpy++;
        }
        tileSize = oldTileSize;
        br.close();
        if (EngineProperties.DEBUG_MODE) {
            Debug.LOG("Prefab parsed");
        }
    }

    public int getCompletion() {
        return (int) completion;
    }

    public Point getSpawn() {
        if (props == null) {
            throw new IllegalStateException("Not loaded yet!");
        }
        return (Point) props.get("spawn");
    }

}
