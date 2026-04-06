package tests;

import org.testng.annotations.Test;
import requestBuilder.APIRequestBuilder;

import static org.hamcrest.Matchers.equalTo;
import static utils.RandomDataGenerator.*;
import static utils.ReadConfigPropertyFile.getGroupId;
import static utils.ReadConfigPropertyFile.getRoleType;
import static utils.ReadEnvFile.*;

public class RegistrationTest {

    private String registeredEmail;

    public void adminLogin() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login successful"), "success", equalTo(true));
    }

    @Test(priority = 1)
    public void adminLoginTest() {
        adminLogin();
    }

    @Test(priority = 2, dependsOnMethods = "adminLoginTest")
    public void userRegistrationTest() {
        registeredEmail = generateEmail();
        APIRequestBuilder.registerUserResponse(generateFirstName(), generateLastName(), registeredEmail, getUserPassword(), getGroupId())
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .body("message", equalTo("Registration submitted successfully. Your account is pending admin approval."), "success", equalTo(true));

    }

    @Test(priority = 3, dependsOnMethods = "userRegistrationTest")
    public void approveUserTest() {
        APIRequestBuilder.approveUserResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User approved successfully"), "data.approvalStatus", equalTo("approved"), "success", equalTo(true));
    }

    @Test(priority = 4, dependsOnMethods = "approveUserTest")
    public void updateUserRoleTest() {
        APIRequestBuilder.updateUserRoleResponse(getRoleType())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User role updated successfully"), "data.role", equalTo("admin"));
    }

    @Test(priority = 5, dependsOnMethods = "updateUserRoleTest")
    public void userLoginTest() {
        APIRequestBuilder.loginUserResponse(registeredEmail, getUserPassword())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login successful"));

        System.out.println("Registered User Email: " + registeredEmail);
    }

    @Test(priority = 6, dependsOnMethods = "userLoginTest")
    public void adminLoginAgainTest() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword());
                adminLogin();
    }

    @Test(priority = 7, dependsOnMethods = "adminLoginAgainTest")
    public void deleteUserTest() {
        APIRequestBuilder.deleteUserResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User deleted successfully"), "success", equalTo(true));

    }
}
