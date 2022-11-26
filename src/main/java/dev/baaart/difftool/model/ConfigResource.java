package dev.baaart.difftool.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigResource {

    private Path PATH_TO_PROPERTIES;

    private Properties properties;

    public ConfigResource(Path PATH_TO_PROPERTIES) {
        this.PATH_TO_PROPERTIES = PATH_TO_PROPERTIES;
        this.properties = new Properties();
    }

    public Properties load(){
        try(InputStream in = new FileInputStream(PATH_TO_PROPERTIES.toString())){
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public void save(Properties properties){
        try (OutputStream output = new FileOutputStream(PATH_TO_PROPERTIES.toString())) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void delete() throws IOException {
        Files.delete(PATH_TO_PROPERTIES);
    }
}
