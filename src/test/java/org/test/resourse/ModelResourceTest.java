package org.test.resourse;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.*;
import org.test.payload.request.ModelRequest;

import static org.hamcrest.Matchers.*;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelResourceTest {



        @Order(1)
    @Test
    void addModel() {

        ModelRequest modelRequest=new ModelRequest();
        modelRequest.setName("Ram");
        modelRequest.setCourse("Java");

        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(modelRequest)
                .when()
                .post("/model/")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

    }

        @Order(2)
    @Test
    void getModelById() {
        RestAssured.given()
                .when()
                .get("/model/7")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("name", notNullValue())
                .body("course", equalTo("Java"));

    }

        @Order(7)
    @Test
    void getAllModels() {
        RestAssured.given()
                .when()
                .get("/model/")
                .then()
                .body("size()", greaterThan(0))
                .statusCode(Response.Status.OK.getStatusCode());
    }

        @Order(3)
    @Test
    void getModelByName() {
        RestAssured.given()
                .when()
                .get("/model/name/Yogi")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("name", equalTo("Yogi"))
                .body("course", notNullValue());

    }
@Order(4)
    @Test
    void getModelsByCourse() {
       RestAssured.given()
                .when()
                .get("/model/course/Java")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("name",everyItem(notNullValue()))
                .body("course",everyItem(equalTo("Java")));
    }

    @Order(5)
    @Test
    void updateModel() {
        ModelRequest modelRequest = new ModelRequest();
        modelRequest.setName("Aman");
        modelRequest.setCourse("Python");

       RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(modelRequest)
                .when()
                .put("/model/10")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("name", equalTo("Aman"))
                .body("course", equalTo("Python"));

    }

    @Order(6)
    @Test
    void deleteModel() {
        RestAssured.given()
                .when()
                .delete("/model/10")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());


        RestAssured.given()
                .when()
                .get("/model/10")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

    }
}