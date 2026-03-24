package basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistration {

    static String authToken;
    static String userId;
    static String registeredEmail;
    static String baseURL = "https://ndosiautomation.co.za";
    String registeredEmail;
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

    @Test(priority = 2)
    public void registerUser(){

        String apiPath = "/APIDEV/register";
<<<<<<< HEAD
          registeredEmail = Faker.instance().internet().emailAddress();
=======
        registeredEmail = Faker.instance().internet().emailAddress();
>>>>>>> origin/main
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

<<<<<<< HEAD


    @Test(priority = 3)
    public void approveUser(){

        String apiPath = "/APIDEV/admin/users/"+userId+"/approve";
=======
    @Test(priority = 3)
    public void approveUserRegistration(){

        String apiPath = "/APIDEV/admin/users/"+userId+"/approve";

>>>>>>> origin/main
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .log().all()
                .put().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");

    }

    @Test(priority = 4)
    public void userLoginTest() {

        String apiPath = "/APIDEV/login";
<<<<<<< HEAD

=======
>>>>>>> origin/main
        String payload = String.format( "{\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"@a12345678\"\n" +
                "}", registeredEmail);

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
<<<<<<< HEAD
        authToken = response.jsonPath().getString("data.token");

    }
=======

    }


>>>>>>> origin/main
}
