package com.fakestore.tests;

import com.fakestore.base.TestBase;
import com.fakestore.models.User;
import com.fakestore.utils.UserTestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("FakeStore API Tests")
@Feature("User Endpoint")
public class UpdateUserTests extends TestBase {

    @Test
    @Story("User updates profile successfully")
    @DisplayName("Update User with Valid Data")
    @Description("Should return 200 and reflect updated fields when called with valid input.")
    @Severity(SeverityLevel.CRITICAL)
    public void updateUser_WithValidData_ShouldReturnSuccess() {
        int userId = UserTestData.getValidUserId();
        User updatedUser = UserTestData.createUpdatedUserData();

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .body(updatedUser)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .body("username", equalTo(updatedUser.getUsername()))
                .body("email", equalTo(updatedUser.getEmail()))
                .body("phone", equalTo(updatedUser.getPhone()));
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
                .body("address.city", notNullValue());
    }

    @Test
    @Story("User update fails for malformed ID")
    @DisplayName("Update User with Malformed ID")
    @Description("Should return 400 or 404 when the ID path parameter is invalid.")
    @Severity(SeverityLevel.MINOR)
    public void updateUser_WithMalformedId_ShouldReturnClientError() {
        String malformedId = UserTestData.getMalformedUserId();
        User updatedUser = UserTestData.createUpdatedUserData();

        given()
                .spec(requestSpec)
                .pathParam("id", malformedId)
                .body(updatedUser)
                .when()
                .put("/{id}")
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }

    @Test
    @Story("User update fails for empty payload")
    @DisplayName("Update User with Empty Body")
    @Description("Should return 400 or 422 when request body is empty.")
    @Severity(SeverityLevel.NORMAL)
    public void updateUser_WithEmptyPayload_ShouldReturnClientError() {
        int userId = UserTestData.getValidUserId();

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .body("{}")
                .when()
                .put("/{id}")
                .then()
                .statusCode(anyOf(is(400), is(422), is(500)));
    }

    @Test
    @Story("User update fails with invalid data types")
    @DisplayName("Update User with Invalid Field Type")
    @Description("Should return 400 or error when a field has the wrong data type (e.g., username as number).")
    @Severity(SeverityLevel.NORMAL)
    public void updateUser_WithInvalidFieldType_ShouldReturnClientError() {
        int userId = UserTestData.getValidUserId();

        String invalidPayload = "{ \"username\": 12345 }"; // Invalid type: should be string

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .body(invalidPayload)
                .when()
                .put("/{id}")
                .then()
                .statusCode(anyOf(is(400), is(422), is(500))); // This will likely fail (FakeStore returns 200)
    }


}
