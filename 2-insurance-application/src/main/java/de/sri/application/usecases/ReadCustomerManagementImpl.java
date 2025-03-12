package de.sri.application.usecases;

import de.sri.domain.entities.*;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.ReadCustomerManagement;

import java.util.List;

public class ReadCustomerManagementImpl implements ReadCustomerManagement {
	private final CustomerRepository customerRepository;

	public ReadCustomerManagementImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer getCustomer(int customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> getCustomersByPolicyStatus(PolicyStatus status) {
		return customerRepository.findByPolicyStatus(status);
	}

	@Override
	public List<Customer> getCustomersByAccidentCostGreaterThan(double cost) {
		return customerRepository.findByAccidentCostGreaterThan(cost);
	}

	@Override
	public List<Customer> getCustomersByTicketSpeedExcessGreaterThan(double speedExcess) {
		return customerRepository.findByTicketSpeedExcessGreaterThan(speedExcess);
	}
}
