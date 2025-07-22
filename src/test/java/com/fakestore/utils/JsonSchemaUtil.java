package com.fakestore.utils;

import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.File;

public class JsonSchemaUtil {

    private static final String SCHEMA_BASE_PATH = "src/test/resources/schemas/";

    public static JsonSchemaValidator matchesJsonSchema(String schemaFileName) {
        return JsonSchemaValidator.matchesJsonSchema(new File(SCHEMA_BASE_PATH + schemaFileName));
    }
}
