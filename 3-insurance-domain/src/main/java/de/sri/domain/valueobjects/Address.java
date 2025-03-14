package de.sri.domain.valueobjects;

import de.sri.domain.exceptions.PropertyNotNullException;
import java.util.Objects;

public class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public Address(String street, String city, String state, String zipCode, String country)
            throws PropertyNotNullException {
        if (street == null) {
            throw new PropertyNotNullException("street");
        }
        if (city == null) {
            throw new PropertyNotNullException("city");
        }
        if (state == null) {
            throw new PropertyNotNullException("state");
        }
        if (zipCode == null) {
            throw new PropertyNotNullException("zipCode");
        }
        if (country == null) {
            throw new PropertyNotNullException("country");
        }
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + " " + zipCode + ", " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city)
                && Objects.equals(state, address.state) && Objects.equals(zipCode, address.zipCode)
                && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, zipCode, country);
    }
}
