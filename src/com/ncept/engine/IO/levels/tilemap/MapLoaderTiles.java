package com.ncept.engine.IO.levels.tilemap;

import com.ncept.engine.IO.levels.MapLoader;
import com.ncept.engine.IO.levels.etc.MapTile;
import com.ncept.engine.IO.levels.etc.Prefab;
import com.ncept.engine.physicsEngine.objects.GameObject;
import com.ncept.engine.physicsEngine.objects.GameObjectAir;
import com.ncept.engine.utils.Debug;
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
public class MapLoaderTiles extends MapLoader {

    private Properties props;

    private File mapFile;
    private File tileFile;

    private Integer tileSize;

    private Map<String, File> prefabs = new HashMap<>();

    private Map<String, Class<? extends GameObject>> tileInfo = new HashMap<>();

    private List<GameObject> backgroundObjects = new ArrayList<>();

    private List<GameObject> foregroundObjects = new ArrayList<>();

    public MapLoaderTiles(String filePath) {
        super(filePath);
        this.mapFile = new File(filePath);
        tileInfo.put("0", GameObjectAir.class);
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

    public Map<String, Class<? extends GameObject>> getTileInfo() {
        return tileInfo;
    }

    public void putObjectClass(String key, Class<? extends GameObject> objectClass) {
        tileInfo.put(key, objectClass);
    }

    public void removeObjectClass(String key) {
        tileInfo.remove(key);
    }

    @Override
    protected void createObjects() {
        Collections.sort(backgroundObjects);
        Collections.sort(foregroundObjects);
        gObjects.addAll(backgroundObjects);
        gObjects.addAll(foregroundObjects);
    }

    @Override
    public String getMapName() {
        if (props == null) {
            throw new IllegalStateException("Not loaded yet!");
        }
        return props.getProperty("name");
    }

    @Override
    public String getBGMName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void loadMap() throws IOException {
        loadProperties();
        loadTileFile();
        createObjects();
        loaded = true;
    }

    @Deprecated
    @Override
    public void loadPrefabs() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Deprecated
    @Override
    public List<MapTile> createPrefabTiles(Prefab prefab, Object map) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadProperties() throws IOException {
        props = new Properties();
        BufferedReader br = new BufferedReader(new FileReader(mapFile));
        String line;
        while ((line = br.readLine()) != null) {
            parseProperty(line);
        }
        if (!props.containsKey("name") || !props.containsKey("file")) {
            throw new RuntimeException("Error loading " + mapFile.getName() + "\nMake sure name and file are set");
        }
        tileFile = new File(mapFile.getParent() + "/" + (props.getProperty("file").endsWith(".tiles") ? props.getProperty("file") : props.getProperty("file") + ".tiles"));
        String tileSizeStr = props.getProperty("tileSize", "32");
        tileSize = Integer.parseInt(tileSizeStr);
    }

    private void parseProperty(String propertyLine) {
        String[] values = propertyLine.trim().split("=");
        switch (values[0]) {
            case "prefab":
                String[] prefabValues = values[1].split(":");
                prefabs.put(prefabValues[1], new File(prefabsFolder + (prefabValues[0].endsWith(".prefab") ? prefabValues[0] : prefabValues[0] + ".prefab")));
                break;
            case "spawn":
                //TODO SPAWN POINT CODE
                break;
            default:
                props.put(values[0], values[1]);
                break;
        }
    }

    private void loadTileFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(tileFile));
        String line;
        int mx, my = 0;
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
        }
    }

    private void createForegroundObject(String objDesc, int x, int y) {
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

    private void createBackgroundObject(String objDesc, int x, int y) {
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

    private void prefabParser(String objDesc, int mx, int my) throws IOException {
        int oldTileSize = tileSize;
        mx *= oldTileSize;
        my *= oldTileSize;
        File prefab = prefabs.get(objDesc);
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
    }

    public List<GameObject> getgObjects() {
        return gObjects;
    }

}
