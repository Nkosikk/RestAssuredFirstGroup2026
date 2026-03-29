package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigPropertyFile {
    private static Properties p;

    static {
        try {
            FileInputStream fr = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/configfiles/config.properties");
            p = new Properties();
            p.load(fr);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyName) {
        return p.getProperty(propertyName);
    }
}
