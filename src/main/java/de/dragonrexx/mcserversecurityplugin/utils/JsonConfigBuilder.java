package de.dragonrexx.mcserversecurityplugin.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonConfigBuilder {

    private File file;
    private JSONObject jsonConfig;
    private final String name;

    public JsonConfigBuilder(String path ,String fileName) {
        this.name = fileName + ".json";

        File file = new File("plugins//" + path + "/" + fileName + ".json");
        try {
            File folder = file.getParentFile();
            if(!folder.exists())
                folder.mkdirs();

            this.file = file;
            if(!(file.exists())) {
                file.createNewFile();
                this.jsonConfig = new JSONObject();
                saveConfig();
            } else {
                this.jsonConfig = new JSONObject(new String(Files.readAllBytes(Paths.get(file.toURI())), "UTF-8".replace("\n", "")));
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonConfig.toString());
            fileWriter.flush();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public Object getObject(String key, Object defaultValue) {
        try {
            return this.jsonConfig.get(key);
        } catch (JSONException exception) {
            jsonConfig.put(key, defaultValue);
            saveConfig();
            return defaultValue;
        }
    }

    public List<Object> getList(String key, List<Object> defaultValue) {
        try {
            return this.jsonConfig.getJSONArray(key).toList();
        } catch (JSONException exception) {
            jsonConfig.put(key, defaultValue);
            saveConfig();
            return defaultValue;
        }
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return (Integer) getObject(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return (String) getObject(key, defaultValue);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return (Boolean) getObject(key, defaultValue);
    }

    public Double getDouble(String key, Double defaultValue) {
        return (Double) getObject(key, defaultValue);
    }

    public JSONObject getJsonConfig() {
        return jsonConfig;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }
}
