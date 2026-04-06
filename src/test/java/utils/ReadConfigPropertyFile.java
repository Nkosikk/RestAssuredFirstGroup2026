package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ReadConfigPropertyFile {
    private static Properties p;

    static {
        loadProperties();
    }

    private static  void loadProperties() {
        if (p != null) {
            return;
        }

        Path configPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "configFiles/config.properties");
        Properties loaded = new Properties();

        try (InputStream inputStream = Files.newInputStream(configPath)) {
            loaded.load(inputStream);
            p = loaded;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties from: " + configPath.toAbsolutePath(), e);
        }
    }

    public static String getProperty(String propertyName) {
        if (p == null) {
            loadProperties();
        }
        return p.getProperty(propertyName);
    }

    public static String getGroupId() {

        return getProperty("api.groupid");
    }

    public static String getRoleType() {

        return getProperty("api.roletype");
    }
}
