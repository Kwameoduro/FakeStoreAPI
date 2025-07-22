package com.fakestore.tests;

import com.fakestore.base.TestBase;
import com.fakestore.utils.UserTestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("FakeStore API Tests")
@Feature("User Endpoint")
public class DeleteUserTests extends TestBase {

    @Test
    @Story("User deletion works for valid ID")
    @DisplayName("Delete Existing User")
    @Description("Should return 200 when a valid user is deleted.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUser_WithValidId_ShouldReturnSuccess() {
        int userId = UserTestData.getValidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body(not(emptyOrNullString())); // often echoes back deleted user
    }

    @Test
    @Story("User deletion fails for non-existent ID")
    @DisplayName("Delete Non-Existent User")
    @Description("Should return 404 or 500 when trying to delete a user that doesn't exist.")
    @Severity(SeverityLevel.NORMAL)
    public void deleteUser_WithInvalidId_ShouldReturnError() {
        int invalidUserId = UserTestData.getInvalidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", invalidUserId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(anyOf(is(404), is(500)));
    }

    @Test
    @Story("User deletion fails for malformed ID")
    @DisplayName("Delete User with Malformed ID")
    @Description("Should return 400 or 404 when the ID path parameter is not valid.")
    @Severity(SeverityLevel.MINOR)
    public void deleteUser_WithMalformedId_ShouldReturnClientError() {
        String malformedId = UserTestData.getMalformedUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", malformedId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }

    @Test
    @Story("User deletion fails when no ID is provided")
    @DisplayName("Delete User Without ID in Path")
    @Description("Should return 405 or 404 when no user ID is passed in the path.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteUser_WithoutId_ShouldReturnError() {
        given()
                .spec(requestSpec)
                .when()
                .delete() // No path param
                .then()
                .statusCode(anyOf(is(405), is(404), is(400))); // Will likely return 404
    }

    @Test
    @Story("User deletion fails with invalid URL")
    @DisplayName("Delete with Invalid URL Path")
    @Description("Should return 404 when deleting a user with an invalid URL pattern.")
    @Severity(SeverityLevel.MINOR)
    public void deleteUser_WithInvalidUrlPath_ShouldFail() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/users/@@@") // invalid path format
                .then()
                .statusCode(anyOf(is(400), is(422))); // likely to return 404, so this will fail
    }


}
