package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class YouTrackApiTests {
    final String BASE_URL = "http://193.233.193.42:9091/api";
    final String TOKEN = "1743848143335.cf6f74d5-c1b8-457f-9d4b-2348fe19440f.602fdb71-abe5-496c-ac8f-531d6762c1ea.cf6f74d5-c1b8-457f-9d4b-2348fe19440f 0-0-0-0-0;1.MCwCFF4xrTpL10lBYfgi/avC/EAfAJl4AhRwXfmtahrrGB6frDTGypC7x4Ir+w==";

    @Test // Проверка текущего пользователя
    void checkCurrentUser() {
        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id", equalTo("2-6"));
    }
    @Test //Получение всех проектов
    void getProjects() {
        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/admin/projects")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
    @Test //Получение всех пользователей
    void getAllUsers() {
        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/users?fields=id,login,email")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }
    @Test //Получение комментариев по ID задачи
    void getComments() {
        String issueId = "DEMO-22";

        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/issues/" + issueId + "/comments?fields=text,author(login)")
                .then()
                .statusCode(200);
    }
    @Test // Получение связей по ID задачи
    void getIssueLinks() {
        String issueId = "DEMO-22";

        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .when()
                .get("/issues/" + issueId + "/links")
                .then()
                .statusCode(200);
    }
    @Test
    void addCommentToIssue() {
        String issueId = "DEMO-22"; // замените на ID своей задачи

        String commentBody = """
        {
          "text": "41"
        }
        """;

        given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Content-Type", "application/json")
                .body(commentBody)
                .when()
                .post("/issues/" + issueId + "/comments")
                .then()
                .statusCode(200);

    }
}