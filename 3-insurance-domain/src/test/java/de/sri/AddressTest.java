package de.sri;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.Address;

public class AddressTest {

    @Test
    void create_valid_address_object() throws PropertyNotNullException {
        Address address = new Address("Musterstraße 1", "Musterstadt", "X", "12345", "Musterland");
        assertEquals("Musterstraße 1", address.getStreet());
        assertEquals("Musterstadt", address.getCity());
        assertEquals("X", address.getState());
        assertEquals("12345", address.getZipCode());
        assertEquals("Musterland", address.getCountry());
    }

    @Test
    void create_invalid_address_object_with_null_street() {
        assertThrows(PropertyNotNullException.class, () -> {
            new Address(null, "Musterstadt", "X", "12345", "Musterland");
        });
    }

    @Test
    void to_string_method() throws PropertyNotNullException {
        Address address = new Address("Musterstraße 1", "Musterstadt", "X", "12345", "Musterland");
        assertEquals("Musterstraße 1, Musterstadt, X 12345, Musterland", address.toString());
    }
}
