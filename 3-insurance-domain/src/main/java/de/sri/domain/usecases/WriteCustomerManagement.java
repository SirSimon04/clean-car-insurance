package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Accident;

public interface WriteCustomerManagement {
	Customer createCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerId);

	void createAccidentForCustomer(int customerId, Accident accident);
}
