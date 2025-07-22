package com.fakestore.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;

    // --- Getters and Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // --- Nested Classes ---

    public static class Name {
        private String firstname;
        private String lastname;

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }

        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
    }

    public static class Address {
        private String city;
        private String street;
        private int number;
        private String zipcode;
        private GeoLocation geolocation;

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }

        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }

        public GeoLocation getGeolocation() { return geolocation; }
        public void setGeolocation(GeoLocation geolocation) { this.geolocation = geolocation; }
    }

    public static class GeoLocation {
        private String lat;

        @JsonProperty("long")
        private String _long;

        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }

        @JsonProperty("long")
        public String getLong() { return _long; }

        @JsonProperty("long")
        public void setLong(String _long) { this._long = _long; }
    }
}
