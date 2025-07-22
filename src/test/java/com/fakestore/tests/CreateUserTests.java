package com.fakestore.tests;

import com.fakestore.base.TestBase;
import com.fakestore.models.User;
import com.fakestore.utils.UserTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("FakeStore API Tests")
@Feature("User Endpoint")
public class CreateUserTests extends TestBase {

    @Test
    @Story("User signs up successfully")
    @DisplayName("Create New User with Valid Data")
    @Description("Should return 200 and the created user ID when valid input is provided.")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser_WithValidData_ShouldReturnSuccess() {
        User newUser = UserTestData.createValidUser();

        Response response = given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().response();

        System.out.println("Mock created user ID: " + response.path("id"));
    }

    @Test
    @Story("User creation fails with missing required fields")
    @DisplayName("Create User with Missing Email")
    @Description("Should return 400 or appropriate error when email is missing from the payload.")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser_WithoutEmail_ShouldReturnClientError() {
        User user = UserTestData.createValidUser();
        user.setEmail(null); // Invalidate the email

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(anyOf(is(400), is(422), is(500))); // FakeStore might return 500
    }

    @Test
    @Story("User creation fails with empty payload")
    @DisplayName("Create User with Empty Body")
    @Description("Should return 400 or 422 when request body is empty.")
    @Severity(SeverityLevel.NORMAL)
    public void createUser_WithEmptyPayload_ShouldReturnClientError() {

        given()
                .spec(requestSpec)
                .body("{}") // Empty JSON
                .when()
                .post()
                .then()
                .statusCode(anyOf(is(400), is(422), is(500)))
                .body(not(emptyOrNullString()));
    }

    @Test
    @Story("User signs up successfully")
    @DisplayName("Create User Returns Only ID")
    @Description("Should return 200 and only contain an ID field.")
    @Severity(SeverityLevel.NORMAL)
    public void createUser_ShouldReturnOnlyIdField() {
        User newUser = UserTestData.createValidUser();

        Response response = given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract().response();

        // Validate only one field is returned (id)
        assertEquals(1, response.jsonPath().getMap("$").size(), "Response should only contain 'id'");
        assertTrue(response.path("id") instanceof Integer, "ID should be a number");
    }

    @Test
    @Story("User creation fails with missing password")
    @DisplayName("Create User with Missing Password")
    @Description("Should return 400 or 422 when password is not provided.")
    @Severity(SeverityLevel.CRITICAL)
    public void createUser_WithoutPassword_ShouldReturnClientError() {
        User user = UserTestData.createValidUser();
        user.setPassword(null); // Intentionally remove password

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(anyOf(is(400), is(422), is(500))); // Will fail because FakeStore returns 200
    }


}
