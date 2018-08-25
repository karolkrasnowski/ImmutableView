package io.github.karolkrasnowski.immutableview.domain;

public class Address {

    private Street street;
    private String city;

    public Address(Street street, String city) {
        this.street = street;
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
