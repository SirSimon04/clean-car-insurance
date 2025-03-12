package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import java.util.List;

public interface ReadCustomerManagement {

	Customer getCustomer(int customerId);

	List<Customer> getAllCustomers();

	List<Customer> getCustomersByPolicyStatus(PolicyStatus status);

	List<Customer> getCustomersByAccidentCostGreaterThan(double cost);

	List<Customer> getCustomersByTicketSpeedExcessGreaterThan(double speedExcess);
}
