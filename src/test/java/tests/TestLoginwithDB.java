package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseConnection;

import java.sql.SQLException;

public class TestLoginwithDB {

    @BeforeClass
    public void setup() throws SQLException {
        DatabaseConnection.dbConnection();
    }

    @Test
    public void loginWithDBTest(){
        UserRegistrationTest testLogin = new UserRegistrationTest();
        testLogin.adminLoginTest();
    }

}
