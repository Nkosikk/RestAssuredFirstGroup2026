package utils;
import io.github.cdimascio.dotenv.Dotenv;



public class ReadEnvFile {

    private static final Dotenv dotenv = Dotenv.configure()
            .filename("envFiles/creds.env")
            .ignoreIfMissing()
            .load();

    public static String getAdminEmail() {
        return getValue("ADMIN_EMAIL");
    }

    public static String getAdminPassword() {
        return getValue("ADMIN_PASSWORD");
    }

    public static String getUserPassword() {
        return getValue("USER_PASSWORD");
    }
    private static String getValue(String key) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = dotenv.get(key);
        }

        if (value == null || value.isBlank()) {
            throw new RuntimeException(key + " is not set in creds.env file");
        }

        return value;
    }
}
