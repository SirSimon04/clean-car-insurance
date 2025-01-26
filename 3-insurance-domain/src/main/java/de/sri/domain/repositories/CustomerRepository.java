package de.sri.domain.repositories;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
	Customer save(Customer customer);

	Optional<Customer> findById(int id);

	List<Customer> findAll();

	void delete(int id);

	List<Customer> findByPolicyStatus(PolicyStatus status);

	List<Customer> findByAccidentCostGreaterThan(double cost);

	List<Customer> findByTicketSpeedExcessGreaterThan(double speedExcess);
}
