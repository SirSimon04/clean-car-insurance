package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.exceptions.CustomerNotFoundException;
import java.util.List;

public interface ReadCustomerManagement {

	Customer getCustomer(int customerId) throws CustomerNotFoundException;

	List<Customer> getAllCustomers();

	List<Customer> getCustomersByPolicyStatus(PolicyStatus status);

	List<Customer> getCustomersByAccidentCostGreaterThan(double cost);

	List<Customer> getCustomersByTicketSpeedExcessGreaterThan(double speedExcess);
}
