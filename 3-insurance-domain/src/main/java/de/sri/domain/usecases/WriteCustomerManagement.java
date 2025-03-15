package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.BaseDomainException;
import de.sri.domain.exceptions.CustomerNotFoundException;

public interface WriteCustomerManagement {
    Customer createCustomer(Customer customer) throws BaseDomainException;

    void updateCustomer(Customer customer) throws BaseDomainException;

    void deleteCustomer(int customerId) throws CustomerNotFoundException;

    void createAccidentForCustomer(int customerId, Accident accident) throws BaseDomainException;
}
