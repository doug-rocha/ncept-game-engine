package com.ncept.engine.IO.levels.tilemap;

import com.ncept.engine.IO.levels.MapLoader;
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
                //TODO SPAWN POINT CODE
                break;
            default:
                props.put(values[0], values[1]);
                break;
        }
    }

}
