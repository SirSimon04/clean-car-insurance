package de.sri.domain.directors;

import java.time.LocalDate;

import de.sri.domain.entities.Customer;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.EmailAddress;
import de.sri.domain.valueobjects.PersonName;

public class TestCustomerDirector extends CustomerDirector {
    public TestCustomerDirector(Customer.Builder builder) {
        super(builder);
    }

    public Customer createMockUser() throws PropertyNotNullException, InvalidEmailAddress {
        PersonName name = new PersonName("Anna", "Schmidt");
        EmailAddress email = new EmailAddress("anna.schmidt@example.de");
        Address address = new Address("Example Street", "12345", "Example City", "1234456", "Example Country");

        return super.builder.withId(1).withName(name).withDateOfBirth(LocalDate.of(1987, 6, 15)).withEmail(email)
                .withAddress(address).build();
    }

    public Customer createYoungDriver() throws PropertyNotNullException, InvalidEmailAddress {
        PersonName name = new PersonName("John", "Doe");
        EmailAddress email = new EmailAddress("john.doe@example.com");
        Address address = new Address("Example Street", "12345", "Example City", "1234456", "Example Country");

        return super.builder.withId(1).withName(name).withDateOfBirth(LocalDate.now().minusYears(18)).withEmail(email)
                .withAddress(address).build();
    }

    public Customer createDriverUnder18() throws PropertyNotNullException, InvalidEmailAddress {
        PersonName name = new PersonName("John", "Doe");
        EmailAddress email = new EmailAddress("john.doe@example.com");
        Address address = new Address("Example Street", "12345", "Example City", "1234456", "Example Country");

        return builder.withId(1).withName(name).withDateOfBirth(LocalDate.now().minusYears(15)).withEmail(email)
                .withAddress(address).build();
    }

    public Customer createSeniorDriver() throws PropertyNotNullException, InvalidEmailAddress {
        PersonName name = new PersonName("John", "Doe");
        EmailAddress email = new EmailAddress("john.doe@example.com");
        Address address = new Address("Example Street", "12345", "Example City", "1234456", "Example Country");

        return builder.withId(2).withName(name).withDateOfBirth(LocalDate.now().minusYears(85)).withEmail(email)
                .withAddress(address).build();
    }
}
