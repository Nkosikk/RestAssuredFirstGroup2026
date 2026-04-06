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

    // This is a helper method, that performs the admin login and asserts the response.
    public void adminLogin() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("Login successful"), "success", equalTo(true));
    }

    // The adminLoginTest method is the first test in the sequence, which calls the adminLogin helper method to perform the login and assert the response.
    @Test(priority = 1)
    public void adminLoginTest() {
        adminLogin();
    }

    // The userRegistrationTest method is the second test in the sequence, which depends on the successful completion of the adminLoginTest. It generates a random email for the new user, sends a registration request using the APIRequestBuilder, and asserts the response to ensure that the registration was submitted successfully and is pending admin approval.
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

    // The approveUserTest method is the third test in the sequence, which depends on the successful completion of the userRegistrationTest. It sends a request to approve the newly registered user and asserts the response to ensure that the user was approved successfully and that their approval status is updated to "approved".
    @Test(priority = 3, dependsOnMethods = "userRegistrationTest")
    public void approveUserTest() {
        APIRequestBuilder.approveUserResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User approved successfully"), "data.approvalStatus", equalTo("approved"), "success", equalTo(true));
    }

    // The updateUserRoleTest method is the fourth test in the sequence, which depends on the successful completion of the approveUserTest. It sends a request to update the user's role and asserts the response to ensure that the user role was updated successfully and that the new role is "admin".
    @Test(priority = 4, dependsOnMethods = "approveUserTest")
    public void updateUserRoleTest() {
        APIRequestBuilder.updateUserRoleResponse(getRoleType())
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("message", equalTo("User role updated successfully"), "data.role", equalTo("admin"));
    }

    // The userLoginTest method is the fifth test in the sequence, which depends on the successful completion of the updateUserRoleTest. It sends a login request for the newly registered user and asserts the response to ensure that the login was successful. It also prints the registered user's email to the console for verification.
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

    // The adminLoginAgainTest method is the sixth test in the sequence, which depends on the successful completion of the userLoginTest. It performs another admin login to ensure that the admin can still log in successfully after the user registration and role update processes.
    @Test(priority = 6, dependsOnMethods = "userLoginTest")
    public void adminLoginAgainTest() {
        APIRequestBuilder.loginUserResponse(getAdminEmail(), getAdminPassword());
                adminLogin();
    }

    // The deleteUserTest method is the seventh and final test in the sequence, which depends on the successful completion of the adminLoginAgainTest. It sends a request to delete the newly registered user and asserts the response to ensure that the user was deleted successfully.
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
