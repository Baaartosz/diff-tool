package dev.baaart.difftool.model;

import org.apache.commons.lang3.SystemUtils;

import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class ConfigManager {
    private static final String APP_NAME = "difftool";
    private static final String PROPERTIES_FILE_NAME = APP_NAME + ".properties";
    private static ConfigManager configManagerInstance = null;
    private final Properties properties;
    private final Path difftoolDirectory;

    private ConfigManager() {
        properties = new Properties();
        difftoolDirectory = getPropertiesDirectory();
        loadPropertiesFileFromStorage();
    }

    public static ConfigManager getInstance() {
        if (configManagerInstance == null) {
            configManagerInstance = new ConfigManager();
        }
        return configManagerInstance;
    }


    public Properties get() {
        return properties;
    }

    public void save(Properties properties) {
        savePropertiesFile(Paths.get(difftoolDirectory + "/" + PROPERTIES_FILE_NAME), properties);
    }

    // TODO - to be moved into cleanup class.
    private boolean remove() {
        try {
            deleteDirectoryRecursion(getDifftoolDirectory());
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Path getDifftoolDirectory() {
        return difftoolDirectory;
    }

    public boolean isScratchDirMissing(){
        var value = ConfigManager.getInstance().get().getProperty("scratchFolder");
        return value == null || value.equals("");
    }

    // TODO - to be moved into cleanup class.
    private void deleteDirectoryRecursion(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        Files.delete(path);
    }

    private Path getPropertiesDirectory() {
        try {
            Path propertiesDirectory = Paths.get(getApplicationDirectory() + '/' + APP_NAME);

            if (Files.notExists(propertiesDirectory)) {
                try {
                    Files.createDirectories(propertiesDirectory);

                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());
                }
            }
            return propertiesDirectory;

        } catch (UnsupportedOperatingSystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void loadPropertiesFileFromStorage() {
        Path pathToProperties = Paths.get(difftoolDirectory + "/" + PROPERTIES_FILE_NAME);

        if (Files.notExists(pathToProperties)) {
            savePropertiesFile(pathToProperties, properties);
        }

        try (InputStream input = new FileInputStream(pathToProperties.toString())) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePropertiesFile(Path path, Properties properties) {
        try (OutputStream output = new FileOutputStream(path.toString())) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private String getApplicationDirectory() throws UnsupportedOperatingSystemException {
        if (SystemUtils.IS_OS_WINDOWS) {
            return System.getenv("AppData");
        }
        if (SystemUtils.IS_OS_MAC) {
            return System.getProperty("user.home") + "/Library/Application Support";
        }
        throw new UnsupportedOperatingSystemException(System.getProperty("os.name") + " is unsupported.");
    }

}
