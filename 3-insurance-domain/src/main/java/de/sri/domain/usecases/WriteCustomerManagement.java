package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.CustomerNotFoundException;

public interface WriteCustomerManagement {
    Customer createCustomer(Customer customer);

    void updateCustomer(Customer customer) throws CustomerNotFoundException;

    void deleteCustomer(int customerId) throws CustomerNotFoundException;

    void createAccidentForCustomer(int customerId, Accident accident) throws CustomerNotFoundException;
}
