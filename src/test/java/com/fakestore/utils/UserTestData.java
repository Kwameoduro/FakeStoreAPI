package com.fakestore.utils;

import com.fakestore.models.User;

import java.util.UUID;

public class UserTestData {

    public static final int VALID_USER_ID = 1;
    public static final int INVALID_USER_ID = 9999;
    public static final String MALFORMED_USER_ID = "abc";

    public static int getValidUserId() {
        return VALID_USER_ID;
    }

    public static int getInvalidUserId() {
        return INVALID_USER_ID;
    }

    public static String getMalformedUserId() {
        return MALFORMED_USER_ID;
    }

    public static User createValidUser() {
        User user = new User();
        user.setEmail(generateRandomEmail());
        user.setUsername("testuser_" + UUID.randomUUID());
        user.setPassword("TestPass123!");
        user.setPhone("123-456-7890");

        // Set name
        User.Name name = new User.Name();
        name.setFirstname("Test");
        name.setLastname("User");
        user.setName(name);

        // Set address and geolocation
        User.Address address = new User.Address();
        address.setCity("Accra");
        address.setStreet("Test Street");
        address.setNumber(42);
        address.setZipcode("00233");

        User.GeoLocation geo = new User.GeoLocation();
        geo.setLat("5.5600");
        geo.setLong("-0.2050");
        address.setGeolocation(geo);

        user.setAddress(address);

        return user;
    }

    public static String generateRandomEmail() {
        return "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static User createUpdatedUserData() {
        User updatedUser = createValidUser(); // start from valid template
        updatedUser.setUsername("updated_" + UUID.randomUUID());
        updatedUser.setPhone("024-000-0000");
        updatedUser.setEmail("updated_" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
        return updatedUser;
    }


    public static User createEmptyUser() {
        return new User(); // Empty object
    }


}
