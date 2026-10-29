package com.portfolio.api.services;

import com.portfolio.api.model.PostModel;
import com.portfolio.config.ConfigManager;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostService {

    private final String baseUrl;

    public PostService() {
        this.baseUrl = ConfigManager.getProperty("api.baseUrl");
    }

    @Step("GET /posts/{id} - Obtener publicación por ID")
    public Response getPostById(int id) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .when()
                .get("/posts/" + id);
    }

    @Step("POST /posts - Crear una nueva publicación")
    public Response createPost(PostModel post) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(post)
                .when()
                .post("/posts");
    }
}
