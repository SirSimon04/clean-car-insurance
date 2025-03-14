package de.sri.domain.directors;

import java.time.LocalDate;

import de.sri.domain.entities.Customer;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.Address;

public class TestCustomerDirector extends CustomerDirector {
    public TestCustomerDirector(Customer.Builder builder) {
        super(builder);
    }

    public Customer createMockUser() throws PropertyNotNullException {
        return super.builder.withId(1).withFirstName("Anna").withLastName("Schmidt")
                .withDateOfBirth(LocalDate.of(1987, 6, 15)).withEmail("anna.schmidt@example.de")
                .withAddress(new Address("Example Street", "12345", "Example City", "1234456", "Example Country"))
                .build();
    }

    public Customer createYoungDriver() throws PropertyNotNullException {
        return builder.withId(1).withFirstName("John").withLastName("Doe")
                .withDateOfBirth(LocalDate.now().minusYears(20)).withEmail("john.doe@example.com")
                .withAddress(new Address("Street", "City", "State", "Zip", "Country")).build();
    }

    public Customer createDriverUnder18() throws PropertyNotNullException {
        return builder.withId(1).withFirstName("John").withLastName("Doe")
                .withDateOfBirth(LocalDate.now().minusYears(15)).withEmail("john.doe@example.com")
                .withAddress(new Address("Street", "City", "State", "Zip", "Country")).build();
    }

    public Customer createSeniorDriver() throws PropertyNotNullException {
        return builder.withId(2).withFirstName("Jane").withLastName("Doe")
                .withDateOfBirth(LocalDate.now().minusYears(85)).withEmail("jane.doe@example.com")
                .withAddress(new Address("Street", "City", "State", "Zip", "Country")).build();
    }
}
