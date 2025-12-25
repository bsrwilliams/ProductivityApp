package org.bw.productivity.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {

    private static final String FOLDER_NAME = "ProductivityApp";
    private static final String FILE_NAME = "config.properties";

    private final Path configFilePath;
    private final Properties properties;

    public ConfigManager() {
        this.configFilePath = ConfigPathUtil.getUserConfigPath(FOLDER_NAME, FILE_NAME);
        this.properties = new Properties();
    }

    public void load() {
        try {
            // Creates directory if not exists
            Files.createDirectories(configFilePath.getParent());

            // Ensure file exists or create a new file with default values set
            if (Files.exists(configFilePath)) {
                try (InputStream inputStream = Files.newInputStream(configFilePath)) {
                    properties.load(inputStream);
                }
            } else {
                try (InputStream inputStream = getClass().getResourceAsStream("/org/bw/productivity/config.properties")) {
                    if (inputStream != null) {
                        properties.load(inputStream);
                        save();
                    } else {
                        System.err.println("No default config found!");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String keyName) {
        return properties.getProperty(keyName);
    }

    public void setProperty(String keyName, String value) {
        properties.setProperty(keyName, value);
        save();
    }

    private void save() {
        try (OutputStream outputStream = Files.newOutputStream(configFilePath)) {
            properties.store(outputStream, "App Configuration");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
