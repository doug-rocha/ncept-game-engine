package com.ncept.engine.IO.levels;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public class JSONMapParser {

    protected Gson gson;

    protected Class classToLoad;

    public JSONMapParser(Class classToLoad) {
        this.classToLoad = classToLoad;
        gson = new Gson();
    }

    public Object loadMap(String filePath) throws IOException {
        return gson.fromJson(new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8), classToLoad);
    }

    public Object loadMap(File file) throws IOException {
        return loadMap(file.getAbsolutePath());
    }
}
