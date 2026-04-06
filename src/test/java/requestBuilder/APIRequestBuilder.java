package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import payloadBuilder.APIPayloadBuilder;
import utils.PayloadStore;
import utils.ReadConfigPropertyFile;

import static common.BaseURIs.baseURI;
import static constants.Endpoints.*;
import static payloadBuilder.APIPayloadBuilder.loginUserPayload;
import static payloadBuilder.APIPayloadBuilder.registerUserPayload;
import static payloadBuilder.APIPayloadBuilder.updateUserPayload;

public class APIRequestBuilder {

    static String authToken;
    static String userId;


    // This method is used to send a registration request for a new user.
    // It takes the user's first name, last name, email, password, and group ID as parameters,
    public static Response registerUserResponse(String firstName, String lastName, String email, String password, String groupId) {

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .basePath(registerendpoint)
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .body(registerUserPayload(firstName, lastName, email, password, groupId))
                .log().all()
                .post()
                .then().extract().response();

        userId = response.jsonPath().getString("data.id"); // Extract the user ID from the response and store it in a static variable for later use.
        return response;
    }

    // The loginUserResponse method sends a login request with the provided email and password,
    // extracts the authentication token from the response,and stores it in a static variable for later use.
    public static Response loginUserResponse(String email, String password) {

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .basePath(loginendpoint)
                //.header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .body(loginUserPayload(email, password))
                .log().all()
                .post()
                .then().extract().response();

        authToken = response.jsonPath().getString("data.token"); // Extract the authentication token from the response and store it in a static variable for later use.
        return response;
    }

    // This method is used to send a request to approve a user's registration.
    public static Response approveUserResponse(){

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .basePath(adminendpoint + userId + approveusersendpoint)
                .header("Authorization", "Bearer " + authToken)
                .log().all()
                .put()
                .then().extract().response();

        return response;
    }

    public static Response updateUserRoleResponse(String role) {

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .basePath(adminendpoint + userId + roleendpoint)
                .header("Authorization", "Bearer " + authToken)
                .body(updateUserPayload(role))
                .log().all()
                .put()
                .then().extract().response();

        return response;
    }

    // This method is used to send a request to delete a user.
    public static Response deleteUserResponse(){

        Response response = RestAssured.given()
                .baseUri(baseURI)
                .basePath(adminendpoint + userId)
                .header("Authorization", "Bearer " + authToken)
                .log().all()
                .delete()
                .then().extract().response();

        return response;
    }

}
