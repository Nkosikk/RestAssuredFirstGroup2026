package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.PayloadStore;

public class APIRequestBuilder {

    public static Response loginUser(String email, String password) {
        String payload = PayloadStore.loginUserPayload(email, password);

             Response response = RestAssured.given()
                     .baseUri()
                     .contentType(ContentType.JSON)
                     .body(payload)
                     .post("/api/login")
                     .then()
                     .extract()
                     .response();
    }

}
