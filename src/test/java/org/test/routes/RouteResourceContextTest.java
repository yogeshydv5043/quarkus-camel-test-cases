package org.test.routes;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RouteResourceContextTest {

    @Test
    public void testPostSendData() {

        //Sending Text In Post EndPoint
        RestAssured.given()
                .contentType(ContentType.TEXT)
                .port(9903)
                .body("This is normal Data")
                .when()
                .post("/api/v1/sendData")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }
}