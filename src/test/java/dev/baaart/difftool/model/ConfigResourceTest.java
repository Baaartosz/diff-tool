package dev.baaart.difftool.model;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigResourceTest {

    private Path PATH_TO_RESOURCE = Paths.get("C:\\Dev\\projects\\diff-tool\\src\\test\\resources\\config.properties");
    private ConfigResource configResource;

    @BeforeEach
    void setup() {
        configResource = new ConfigResource(PATH_TO_RESOURCE);

        Properties properties = new Properties();
        properties.setProperty("mode.1.expected", "(1a)");
        properties.setProperty("mode.1.actual", "(1b)");

        try (OutputStream output = new FileOutputStream(PATH_TO_RESOURCE.toString())) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Test
    void loadFileFromSystemAndCheckForSampleProperties() {
        Properties properties = configResource.load();

        assertEquals(properties.getProperty("mode.1.expected"), "(1a)");
        assertEquals(properties.getProperty("mode.1.actual"), "(1b)");
    }

    @Test
    void saveFileToSystemAndCheckForUpdatedValues() {
        Properties properties = configResource.load();
        properties.setProperty("mode.1.expected", "(1a :) )");

        configResource.save(properties);

        properties = configResource.load();
        assertEquals(properties.getProperty("mode.1.expected"), "(1a :) )");
    }

    @SneakyThrows // TODO FIXME SneakyThrows is not working.
    @Test
    void deletePropertiesFileFromSystem() {
        try {
            configResource.delete();
        } catch (Exception ex) {
            fail();
        }
        assertTrue(Files.notExists(PATH_TO_RESOURCE));
    }


}