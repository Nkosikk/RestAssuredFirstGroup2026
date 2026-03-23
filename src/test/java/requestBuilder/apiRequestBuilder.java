package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static common.BaseURIs.baseURL;
import static payloadBuilder.PayloadBuilder.loginUserPayload;
import static payloadBuilder.PayloadBuilder.registerUserPayload;


public class apiRequestBuilder {

    static String authToken;
    static String registeredUserId;

    public static Response loginUserResponse(String email, String password) {

        String apiPath = "/APIDEV/login";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(loginUserPayload(email, password))
                .log().all()
                .post()
                .then().extract().response();
        authToken = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response registerUserResponse(String firstName, String lastName, String email, String password, String groupId) {

        String apiPath = "/APIDEV/register";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(registerUserPayload(firstName, lastName, email, password, groupId))
                .log().all()
                .post()
                .then().extract().response();
        registeredUserId = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response approveUserRegistrationResponse() {

        String apiPath = "/APIDEV/admin/users/"+registeredUserId+"/approve";
        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .log().all()
                .put()
                .then().extract().response();
    }


}
