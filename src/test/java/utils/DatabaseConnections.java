package utils;

import org.testng.annotations.Test;

import java.sql.*;

public class DatabaseConnections {


    public static String email;
    public static String password;

    @Test
    public static void dbConnection() throws SQLException {
        String dbURL = ReadConfigPropertyFile.getProperty("db.baseurl");
        String dbUsername = ReadConfigPropertyFile.getProperty("db.username");
        String dbPassword = ReadConfigPropertyFile.getProperty("db.password");

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM loginUser where id = 1");

            while (resultSet.next()) {
                email = resultSet.getString("email");
                password = resultSet.getString("password");
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}
