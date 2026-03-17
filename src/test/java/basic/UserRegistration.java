package basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistration {

    static String authToken;
    static String userId;
    static String baseURL = "https://ndosiautomation.co.za";
    @Test
    public void adminLoginTest() {

        String apiPath = "/APIDEV/login";
        String payload = "{\n" +
                "    \"email\": \"spare@admin.com\",\n" +
                "    \"password\": \"@12345678\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
        authToken = response.jsonPath().getString("data.token");

    }

    @Test(priority = 1)
    public void registerUser(){

        String apiPath = "/APIDEV/register";
        String registeredEmail = Faker.instance().internet().emailAddress();
        String payload = String.format( "{\n" +
                "    \"firstName\": \"dsfdsa\",\n" +
                "    \"lastName\": \"sdfdsaf\",\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"@a12345678\",\n" +
                "    \"confirmPassword\": \"@a12345678\",\n" +
                "    \"phone\": \"\",\n" +
                "    \"groupId\": \"5328c91e-fc40-11f0-8e00-5000e6331276\"\n" +
                "}", registeredEmail);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 201, "Status code should be 201");
        userId = response.jsonPath().getString("data.id");
        System.out.println("Registered User ID: " + userId);
    }
    @Test(priority = 2)
    public void ActivateUser(){
        String apiPath = "/APIDEV/admin/users/userId/status";
        String payload = "{\n" +
                "  \"isActive\": true\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath.replace("userId", userId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .log().all()
                .put().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
        System.out.println("Activated User ID: " + userId);
    }
    @Test(priority = 3)
    public void ApproveUser(){
        String apiPath = "/APIDEV/admin/users/userId/approve";
        String payload = "{\n" +
                "  \"isApproved\": true\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath.replace("userId", userId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(payload)
                .log().all()
                .put().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
        System.out.println("Approved User ID: " + userId);
    }
}
