package de.sri.application.usecases;

import de.sri.domain.entities.*;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.valueobjects.Premium;

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
	public void addPolicyToCustomer(int customerId, Policy policy) {		
		Customer customer = getCustomer(customerId);
		// Customers has to be 18 years old to craete a policy
		if (customer.getAge() < 18) {
			throw new IllegalArgumentException("Customer has to be 18 years old to create a policy!");
		}

		// Car value cannot be more than 100.000
		if(policy.getCarValue() > 100000) {
			throw new IllegalArgumentException("Car value cannot be more than 100,000!");
		}

		// Calculate Premium price for the Customer
		PolicyProgram program = policy.getProgram();
		double premiumPrice = 0;
		switch (program) {
			case BASIC:
				premiumPrice += policy.getCarValue() * 0.05;
				break;
			case STANDARD:
				premiumPrice += policy.getCarValue() * 0.1;
				break;
			case DELUXE:
				premiumPrice += policy.getCarValue() * 0.15;
				break;		
			default:
				break;
		}
		
		// Check if young or senior driver fee should be added
		if(customer.getAge() < 21 || customer.getAge() > 80) {
			premiumPrice += premiumPrice * 0.1;
		}

		// Check if car value is more than 40.000
		if(policy.getCarValue() > 40000) {
			premiumPrice += policy.getCarValue() * 0.01;
		}

		policy.setPremium(new Premium(premiumPrice, "EUR")); // REVISIT: Currency should be a constant
		customer.addPolicy(policy);
		updateCustomer(customer);
	}

	@Override
	public void removePolicyFromCustomer(int customerId, Policy policy) {
		Customer customer = getCustomer(customerId);
		customer.removePolicy(policy.getId());
		updateCustomer(customer);
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
