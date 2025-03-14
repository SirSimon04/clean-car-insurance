package de.sri.domain.repositories;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer) throws PropertyNotNullException, InvalidEmailAddress;

    Optional<Customer> findById(int id);

    List<Customer> findAll();

    void delete(int id) throws CustomerNotFoundException;

    List<Customer> findByPolicyStatus(PolicyStatus status);

    List<Customer> findByAccidentCostGreaterThan(double cost);

    List<Customer> findByTicketSpeedExcessGreaterThan(double speedExcess);
}
