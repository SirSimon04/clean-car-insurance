package de.sri.domain.directors;

import de.sri.domain.entities.Customer;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.EmailAddress;
import de.sri.domain.valueobjects.PersonName;

import java.time.LocalDate;

public class CustomerDirector {
    protected final Customer.Builder builder;

    public CustomerDirector(Customer.Builder builder) {
        this.builder = builder;
    }

    public Customer buildTemporary(PersonName name, LocalDate dateOfBirth, EmailAddress email, Address address) {
        return builder.withId(0).withName(name).withDateOfBirth(dateOfBirth).withEmail(email).withAddress(address)
                .build();
    }

    public Customer buildNew(int id, PersonName name, LocalDate dateOfBirth, EmailAddress email, Address address) {
        return builder.withId(id).withName(name).withDateOfBirth(dateOfBirth).withEmail(email).withAddress(address)
                .build();
    }

    public Customer buildNewFromObject(int id, Customer customer) throws PropertyNotNullException, InvalidEmailAddress {
        PersonName name = new PersonName(customer.getName().getFirstName(), customer.getName().getLastName());
        EmailAddress email = new EmailAddress(customer.getEmail().getEmailAddress());
        return builder.withId(id).withName(name).withDateOfBirth(customer.getDateOfBirth()).withEmail(email)
                .withAddress(customer.getAddress()).build();
    }

}
