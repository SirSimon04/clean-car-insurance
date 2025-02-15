package de.sri.domain.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Ticket;
import java.util.List;

public interface CustomerManagement {
	Customer createCustomer(Customer customer);

	Customer getCustomer(int customerId);

	List<Customer> getAllCustomers();

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerId);

	void createAccidentForCustomer(int customerId, Accident accident);

	List<Customer> getCustomersByPolicyStatus(PolicyStatus status);

	List<Customer> getCustomersByAccidentCostGreaterThan(double cost);

	List<Customer> getCustomersByTicketSpeedExcessGreaterThan(double speedExcess);
}
