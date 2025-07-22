package com.fakestore.tests;


import com.fakestore.base.TestBase;
import com.fakestore.models.User;
import com.fakestore.utils.JsonSchemaUtil;
import com.fakestore.utils.UserTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Epic("FakeStore API Tests")
@Feature("User Endpoint")
public class GetUserTests extends TestBase {

    @Test
    @Story("User retrieves list of users")
    @DisplayName("Get All Users")
    @Description("Should return 200 and a non-empty list of users.")
    @Severity(SeverityLevel.CRITICAL)
    public void getAllUsers_ShouldReturnValidUserList() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get();

        response.then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .body("[0].email", notNullValue())
                .body("[0].username", notNullValue());
    }


    @Test
    @Story("User retrieves profile by ID")
    @DisplayName("Get Single User by ID")
    @Description("Should return 200 and valid user details when called with a valid user ID.")
    @Severity(SeverityLevel.CRITICAL)
    public void getUserById_ShouldReturnValidUser() {
        int userId = UserTestData.getValidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId))
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("address", notNullValue());
    }


    @Test
    @Story("User retrieves profile by ID")
    @DisplayName("Get User by ID - Schema Validation")
    @Description("Should match the expected JSON schema structure.")
    @Severity(SeverityLevel.NORMAL)
    public void getUserById_ShouldMatchSchema() {
        int userId = UserTestData.getValidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body(JsonSchemaUtil.matchesJsonSchema("user-schema.json"));
    }

    @Test
    @Story("User retrieval fails for non-existent ID")
    @DisplayName("Get User by Invalid ID")
    @Description("Should return 404 when a user is not found.")
    @Severity(SeverityLevel.NORMAL)
    public void getUserByInvalidId_ShouldReturn404() {
        int invalidUserId = UserTestData.getInvalidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", invalidUserId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404)
                .body("message", notNullValue());
    }

    @Test
    @Story("User retrieval fails for malformed ID")
    @DisplayName("Get User by Malformed ID")
    @Description("Should return 400 or 404 when an ID is not numeric.")
    @Severity(SeverityLevel.MINOR)
    public void getUserByMalformedId_ShouldReturnClientError() {
        String malformedId = UserTestData.getMalformedUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", malformedId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }
}
