package de.sri.application.usecases;

import de.sri.domain.entities.*;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.CustomerManagement;

import java.util.List;

public class CustomerManagementImpl implements CustomerManagement {
	private final CustomerRepository customerRepository;

	public CustomerManagementImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
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
	public void updateCustomer(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(int customerId) {
		customerRepository.delete(customerId);
	}

	@Override
	public void createAccidentForCustomer(int customerId, Accident accident) {
		Customer customer = getCustomer(customerId);
		customer.addAccident(accident);
		updateCustomer(customer);
	}

	@Override
	public void createTicketForCustomer(int customerId, Ticket ticket) {
		Customer customer = getCustomer(customerId);
		customer.addTicket(ticket);
		updateCustomer(customer);
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
