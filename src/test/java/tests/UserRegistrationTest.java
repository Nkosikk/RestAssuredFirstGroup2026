package tests;

import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.DatabaseConnection;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserRegistrationTest {

    static String registeredEmail;

    @BeforeClass
    public void setup() throws SQLException {
        DatabaseConnection.dbConnection();
    }
    @Test
    public void adminLoginTest(){

        ApiRequestBuilder.loginUserResponse(DatabaseConnection.getEmail, DatabaseConnection.getPassword)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "adminLoginTest")
    public void userRegistration(){
        registeredEmail = Faker.instance().internet().emailAddress();
        ApiRequestBuilder.registerUserResponse("Register","Jsonapi",registeredEmail,"@87654321", "5328c91e-fc40-11f0-8e00-5000e6331276")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "userRegistration")
    public void approveUserRegistration(){
        ApiRequestBuilder.approveUserRegistrationResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(dependsOnMethods = "approveUserRegistration")
    public void userLoginTest(){

        ApiRequestBuilder.loginUserResponse(registeredEmail, "@87654321")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

}
