package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;

public interface WriteCustomerManagement {
    Customer createCustomer(Customer customer) throws PropertyNotNullException, InvalidEmailAddress;

    void updateCustomer(Customer customer)
            throws CustomerNotFoundException, PropertyNotNullException, InvalidEmailAddress;

    void deleteCustomer(int customerId) throws CustomerNotFoundException;

    void createAccidentForCustomer(int customerId, Accident accident)
            throws CustomerNotFoundException, PropertyNotNullException, InvalidEmailAddress;
}
