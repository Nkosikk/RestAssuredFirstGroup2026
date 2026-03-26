package utils;

import java.sql.*;

public class DatabaseConnection {

    public static String Email;
    public static String Password;

    public static void dbConnection() throws SQLException {
        String dbURL = "jdbc:mysql://102.222.124.22:3306/ndosian6b8b7_teaching";
        String dbUsername = "ndosian6b8b7_teaching";
        String dbPassword = "^{SF0a=#~[~p)@l1";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM M_Users WHERE UserID = 1")) {

                while (resultSet.next()) {
                    Email = resultSet.getString("email");
                    Password = resultSet.getString("password");
                    System.out.println("Email: " + Email + ", Password: " + Password);
                }
            } catch (SQLException e) {
                System.out.println("Error executing query: " + e.getMessage());
            }
        }

    }

}
