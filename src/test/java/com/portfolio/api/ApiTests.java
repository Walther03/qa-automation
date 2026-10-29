package com.portfolio.api;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("API Suite - JSONPlaceholder Services")
public class ApiTests {

    @Test(groups = "api")
    @Story("Consulta de Publicaciones")
    @Description("Validar respuesta exitosa (HTTP 200) al obtener el post ID 1")
    public void testGetPost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test(groups = "api")
    @Story("Creación de Publicaciones")
    @Description("Validar creación simulada (HTTP 201) de un nuevo recurso")
    public void testCreatePost() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        String requestBody = "{\n" +
                "  \"title\": \"QE Portfolio Test\",\n" +
                "  \"body\": \"Automated API Execution\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
            .header("Content-Type", "application/json; charset=UTF-8")
            .body(requestBody)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .body("title", equalTo("QE Portfolio Test"));
    }
}
