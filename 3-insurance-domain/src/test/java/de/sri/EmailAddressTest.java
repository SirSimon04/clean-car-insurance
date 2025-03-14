package de.sri;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.EmailAddress;

public class EmailAddressTest {
    @Test
    void create_valid_email_address_object() throws InvalidEmailAddress, PropertyNotNullException {
        EmailAddress email = new EmailAddress("test@test.de");
        assertEquals(email.getEmailAddress(), "test@test.de");
    }

    @Test
    void create_invalid_email_address_object() throws InvalidEmailAddress, PropertyNotNullException {
        assertThrows(InvalidEmailAddress.class, () -> new EmailAddress("testtest"));        
    }

    @Test
    void create_invalid_email_address_object_with_null() {
        assertThrows(PropertyNotNullException.class, () -> new EmailAddress(null));        
    }

    @Test
    void get_domain() throws InvalidEmailAddress, PropertyNotNullException {
        EmailAddress email = new EmailAddress("test@test.de");
        assertEquals(email.getDomain(), "test.de");
    }

    @Test
    void to_string_method() throws InvalidEmailAddress, PropertyNotNullException {
        EmailAddress email = new EmailAddress("test@test.de");
        assertEquals(email.toString(), "test@test.de");
    }
}
