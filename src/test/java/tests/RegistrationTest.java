package tests;

import org.testng.annotations.Test;
import requestBuilder.APIRequestBuilder;

import static org.hamcrest.Matchers.equalTo;
import static utils.RandomDataGenerator.*;
import static utils.ReadConfigPropertyFile.getGroupId;
import static utils.ReadConfigPropertyFile.getRoleType;
import static utils.ReadEnvFile.*;

public class RegistrationTest {

    public static String registeredEmail;
    @Test(priority = 1)
    public void adminLoginTest() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login successful"));
    }


    @Test(priority = 2)
    public void userRegistrationTest() {
       registeredEmail = generateEmail();
        APIRequestBuilder.registerUserResponse(generateFirstName(), generateLastName(), registeredEmail, getUserPassword(), getGroupId())
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Registration submitted successfully. Your account is pending admin approval."));
    }

    @Test(priority = 3)
    public void approveUserTest() {
       APIRequestBuilder.approveUserResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User approved successfully"));
    }

     @Test(priority = 4)
    public void updateUserRoleTest() {
       APIRequestBuilder.updateUserRoleResponse(getRoleType())
                 .then()
                 .log().all()
                 .assertThat()
                 .statusCode(200)
                 .body("message", equalTo("User role updated successfully"),"data.role", equalTo("admin"));
    }

     @Test(priority = 5)
    public void userLoginTest() {
         APIRequestBuilder.loginUserResponse(registeredEmail, getUserPassword())
                 .then()
                 .log().all()
                 .assertThat()
                 .statusCode(200)
                 .body("message", equalTo("Login successful"));

    }

    @Test(priority = 6)
    public void adminLoginTest2() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login successful"));
    }

     @Test(priority = 7)
    public void deleteUserTest() {
            APIRequestBuilder.deleteUserResponse()
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("message", equalTo("User deleted successfully"));
    }


}
