package utils;
import io.github.cdimascio.dotenv.Dotenv;



public class ReadEnvFile {

    // Load environment variables from the creds.env file
    private static final Dotenv dotenv = Dotenv.configure()
            .filename("envFiles/creds.env")
            .ignoreIfMissing()
            .load();

    // Method to get the admin email from environment variables or creds.env file
    public static String getAdminEmail() {
        return getValue("ADMIN_EMAIL");
    }

    // Method to get the user email from environment variables or creds.env file
    public static String getAdminPassword() {
        return getValue("ADMIN_PASSWORD");
    }

    // Method to get the user password from environment variables or creds.env file
    public static String getUserPassword() {
        return getValue("USER_PASSWORD");
    }

    // This method retrieves the value of a specified key from the environment variables first, and if it's not found or is blank, it then checks the creds.env file using the Dotenv library. If the value is still not found or is blank after checking both sources, it throws a RuntimeException indicating that the key is not set in the creds.env file.
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
