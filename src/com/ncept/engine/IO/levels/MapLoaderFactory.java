package com.ncept.engine.IO.levels;

import com.ncept.engine.IO.levels.json.MapLoaderJSON;
import com.ncept.engine.IO.levels.tilemap.MapLoaderTiles;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class MapLoaderFactory {

    public static MapLoader createMapLoader(String filePath) {
        String extension;
        int i = filePath.lastIndexOf(".");
        if (i <= 0) {
            throw new RuntimeException("Could not determine file format to load " + filePath);
        }
        extension = filePath.substring(i + 1);
        switch (extension) {
            case "properties":
                return new MapLoaderTiles(filePath);
            case "json":
                return new MapLoaderJSON(filePath);
            default:
                throw new RuntimeException("Could not determine file format to load " + filePath);
        }
    }

}
