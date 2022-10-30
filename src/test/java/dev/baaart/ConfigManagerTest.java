package dev.baaart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConfigManagerTest {

    ConfigManager configManager;

    @BeforeEach
    void setUp() {
        configManager = ConfigManager.getInstance();
    }

    @Test
    void propertyFileNotNull() {
        assertNotNull(configManager.get());
    }

    @Test
    void retrieveSavedPropertiesSuccessfully() {
        var config = configManager.get();

        config.setProperty("scratchPath", "C:\\Users\\bart\\AppData\\Roaming\\JetBrains\\IntelliJIdea2022.2\\scratches");
        configManager.save(config);

        var modifiedConfig = configManager.get();
        assertEquals("C:\\Users\\bart\\AppData\\Roaming\\JetBrains\\IntelliJIdea2022.2\\scratches", modifiedConfig.getProperty("scratchPath"));
    }

}