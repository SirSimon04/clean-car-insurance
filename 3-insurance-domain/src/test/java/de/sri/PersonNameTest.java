package de.sri;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.PersonName;

public class PersonNameTest {

    @Test
    void create_valid_person_name_object() throws PropertyNotNullException {
        PersonName personName = new PersonName("Max", "Mustermann");
        assertEquals("Max", personName.getFirstName());
        assertEquals("Mustermann", personName.getLastName());
    }

    @Test
    void create_person_name_object_with_leading_and_trailing_spaces() throws PropertyNotNullException {
        PersonName personName = new PersonName(" Max ", " Mustermann ");
        assertEquals("Max", personName.getFirstName());
        assertEquals("Mustermann", personName.getLastName());
    }

    @Test
    void get_full_toname() throws PropertyNotNullException {
        PersonName personName = new PersonName("Max", "Mustermann");
        assertEquals("Max Mustermann", personName.getFullName());
    }

    @Test
    void create_invalid_person_name_object_null_first_name() {
        assertThrows(PropertyNotNullException.class, () -> new PersonName(null, null));
    }

    @Test
    void to_string_method() throws PropertyNotNullException {
        PersonName personName = new PersonName("Max", "Mustermann");
        assertEquals("Max Mustermann", personName.toString());
    }

}
