package de.sri.domain.directors;

import de.sri.domain.entities.Customer;
import de.sri.domain.valueobjects.Address;
import java.time.LocalDate;

public class CustomerDirector {
	protected final Customer.Builder builder;

	public CustomerDirector(Customer.Builder builder) {
		this.builder = builder;
	}

	public Customer buildTemporary(String firstName, String lastName, LocalDate dateOfBirth, String email,
			Address address) {
		return builder.withId(0)
				.withFirstName(firstName)
				.withLastName(lastName)
				.withDateOfBirth(dateOfBirth)
				.withEmail(email)
				.withAddress(address)
				.build();
	}

	public Customer buildNew(int id, String firstName, String lastName, LocalDate dateOfBirth, String email,
			Address address) {
		return builder.withId(id)
				.withFirstName(firstName)
				.withLastName(lastName)
				.withDateOfBirth(dateOfBirth)
				.withEmail(email)
				.withAddress(address)
				.build();
	}

	public Customer buildNewFromObject(int id, Customer customer) {
		return builder.withId(id)
				.withFirstName(customer.getFirstName())
				.withLastName(customer.getLastName())
				.withDateOfBirth(customer.getDateOfBirth())
				.withEmail(customer.getEmail())
				.withAddress(customer.getAddress())
				.build();
	}

}
