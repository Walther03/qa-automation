package com.portfolio.api;

import com.portfolio.api.model.PostModel;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Feature("API Suite - REST Services (DTO/POJO)")
public class ApiTests {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(groups = "api")
    @Story("Consulta de Publicaciones")
    @Description("Deserializar respuesta JSON directamente a un objeto Java (POJO)")
    public void testGetPostWithPojo() {
        PostModel responsePost = given()
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .as(PostModel.class);

        Assert.assertEquals(responsePost.getId(), Integer.valueOf(1), "El ID devuelto no coincide.");
        Assert.assertNotNull(responsePost.getTitle(), "El título de la publicación no debe ser nulo.");
    }

    @Test(groups = "api")
    @Story("Creación de Publicaciones")
    @Description("Serializar un objeto POJO a JSON en una petición POST")
    public void testCreatePostWithPojo() {
        PostModel newPost = new PostModel(1, "QE Portfolio Test", "Automated Execution with RestAssured and POJOs");

        PostModel createdPost = given()
            .contentType(ContentType.JSON)
            .body(newPost)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)
                .extract()
                .as(PostModel.class);

        Assert.assertEquals(createdPost.getTitle(), newPost.getTitle(), "El título de la respuesta no coincide con el enviado.");
        Assert.assertNotNull(createdPost.getId(), "El recurso creado debe retornar un ID generado.");
    }
}
