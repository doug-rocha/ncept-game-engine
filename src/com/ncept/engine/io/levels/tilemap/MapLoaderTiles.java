package com.ncept.engine.io.levels.tilemap;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.io.levels.MapLoader;
import com.ncept.engine.utils.Debug;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class MapLoaderTiles extends MapLoader {

    public MapLoaderTiles(String filePath) {
        super(filePath);
    }

    @Override
    protected void loadProperties() throws IOException {
        props = new Properties();
        BufferedReader br = new BufferedReader(new FileReader(mapFile));
        String line;
        while ((line = br.readLine()) != null) {
            parseProperty(line);
        }
        if (!props.containsKey("name") || !props.containsKey("file")) {
            throw new RuntimeException("Error loading " + mapFile.getName() + "\nMake sure name and file are set");
        }
        br.close();
        applyProperties();
    }

    private void parseProperty(String propertyLine) {
        String[] values = propertyLine.trim().split("=");
        switch (values[0]) {
            case "prefab":
                String[] prefabValues = values[1].split(":");
                prefabs.put(prefabValues[1], new File(prefabsFolder + (prefabValues[0].endsWith(".prefab") ? prefabValues[0] : prefabValues[0] + ".prefab")));
                break;
            case "spawn":
                String[] spawnValues = values[1].split(":");
                props.put(values[0], new Point(Integer.parseInt(spawnValues[0]), Integer.parseInt(spawnValues[1])));
                if (EngineProperties.DEBUG_MODE) {
                    Debug.LOG("Player spawn: " + (Point) props.get("spawn"));
                }
                break;
            default:
                props.put(values[0], values[1]);
                break;
        }
    }

}
