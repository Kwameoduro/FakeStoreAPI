package com.fakestore.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public abstract class TestBase {

    protected RequestSpecification requestSpec;

    public TestBase() {
        baseURI = "https://fakestoreapi.com";

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath("/users")
                .addHeader("Content-Type", "application/json")
                .log(LogDetail.ALL)
                .build();

        // Apply filters for Allure and logs
        filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
        );
    }
}
