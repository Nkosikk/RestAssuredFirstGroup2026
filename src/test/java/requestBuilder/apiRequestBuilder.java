package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static common.BaseURIs.baseURL;
import static payloadBuilder.PayloadBuilder.loginUserPayload;
import static payloadBuilder.PayloadBuilder.registerUserPayload;


public class apiRequestBuilder {

    static String authToken;

    public static Response loginUserResponse(String email, String password) {

        String apiPath = "/APIDEV/login";
        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("Content-Type", "application/json")
                .body(loginUserPayload(email, password))
                .log().all()
                .post().prettyPeek();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
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
                .post().prettyPeek();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 201, "Status code should be 201");
        return response;
    }


}
