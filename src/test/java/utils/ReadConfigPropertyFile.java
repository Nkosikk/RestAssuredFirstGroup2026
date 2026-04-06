package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ReadConfigPropertyFile {
    private static Properties p;

    // The static block is executed when the class is loaded, and it calls the loadProperties method to initialize the Properties object with the values from the config.properties file.
    static {
        loadProperties();
    }

    // This method loads the properties from the config.properties file into the Properties object.
    private static  void loadProperties() {
        if (p != null) {
            return;
        }

        Path configPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "configFiles/config.properties");// Construct the path to the config.properties file using the current working directory and the relative path to the file.
        Properties loaded = new Properties();

        // The try-with-resources statement is used to ensure that the InputStream is properly closed after the properties are loaded. If an IOException occurs during the loading process, a RuntimeException is thrown with a message indicating the failure and the absolute path of the config file.
        try (InputStream inputStream = Files.newInputStream(configPath)) {
            loaded.load(inputStream);
            p = loaded;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties from: " + configPath.toAbsolutePath(), e);
        }
    }

    // This method retrieves the value of a specified property from the loaded properties. If the properties have not been loaded yet, it calls the loadProperties method to load them first.
    public static String getProperty(String propertyName) {
        if (p == null) {
            loadProperties();
        }
        return p.getProperty(propertyName);
    }

    // The following methods are convenience methods that call the getProperty method with specific property names to retrieve commonly used configuration values such as the base URL, admin email, admin password, user password, group ID, and role type.
    public static String getGroupId() {

        return getProperty("api.groupid");
    }

    // This method retrieves the base URL for the API from the properties file.
    public static String getRoleType() {

        return getProperty("api.roletype");
    }
}
