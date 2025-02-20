package com.ncept.engine.io.levels.json;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.io.levels.MapLoader;
import com.ncept.engine.utils.Debug;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Used to load JSON files and process its information for creation of the tiles
 *
 * @author Douglas Rocha de Oliveira
 */
public class MapLoaderJSON extends MapLoader {

    /**
     * @param filePath the file path for the JSON map, this file must contains
     * information about the map, and the tiles/prefabs dependencies
     */
    public MapLoaderJSON(String filePath) {
        super(filePath);
    }

    @Override
    protected void loadProperties() throws IOException {
        props = new Properties();
        BufferedReader br = new BufferedReader(new FileReader(mapFile));
        String line, stringJson = "";
        while ((line = br.readLine()) != null) {
            stringJson += line;
        }
        br.close();
        parseJson(stringJson);
    }

    private void parseJson(String json) {
        JSONObject jsonObject = new JSONObject(json);
        if (!jsonObject.has("name") || !jsonObject.has("file")) {
            throw new RuntimeException("Error loading " + mapFile.getName() + "\nMake sure name and file are set");
        }
        props.put("name", jsonObject.getString("name"));
        props.put("file", jsonObject.getString("file"));
        if (jsonObject.has("bgm")) {
            props.put("bgm", jsonObject.getString("bgm"));
        }
        if (jsonObject.has("prefabs")) {
            parseJsonPrefabs(jsonObject.getJSONArray("prefabs"));
        }
        if (jsonObject.has("spawn")) {
            JSONObject spawnPJson = jsonObject.getJSONObject("spawn");
            if (spawnPJson.has("x") && spawnPJson.has("y")) {
                Point spawnPoint = new Point(spawnPJson.getInt("x"), spawnPJson.getInt("y"));
                props.put("spawn", spawnPoint);
                if (EngineProperties.DEBUG_MODE) {
                    Debug.LOG("Player spawn: " + spawnPoint);
                }
            } else {
                if (EngineProperties.DEBUG_MODE) {
                    Debug.LOG("Invalid Spawn point information, ignoring");
                }
            }
        }
        if (jsonObject.has("tileSize")) {
            props.put("tileSize", jsonObject.getString("tileSize"));
        }
        applyProperties();
    }

    private void parseJsonPrefabs(JSONArray jsonPrefabsArray) {
        for (int i = 0; i < jsonPrefabsArray.length(); i++) {
            JSONObject obj = jsonPrefabsArray.getJSONObject(i);
            if (obj.has("file") && obj.has("key")) {
                prefabs.put(obj.getString("key"), new File(prefabsFolder + (obj.getString("file").endsWith(".prefab") ? obj.getString("file") : obj.getString("file") + ".prefab")));
            }
        }
    }
}
