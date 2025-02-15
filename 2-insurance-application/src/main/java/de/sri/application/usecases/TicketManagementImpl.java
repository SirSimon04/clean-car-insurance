package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Ticket;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;

public class TicketManagementImpl implements TicketManagement {

    public final int INCREASE_PREMIUM = 10;

    private final CustomerRepository customerRepository;
    private final PolicyManagement policyManagement;

    public TicketManagementImpl(CustomerRepository customerRepository, PolicyManagement policyManagement) {
        this.customerRepository = customerRepository;
        this.policyManagement = policyManagement;
    }

    private Customer getCustomer(int customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

    @Override
	public void createTicketForCustomer(int customerId, Ticket ticket) {
		Customer customer = getCustomer(customerId);        
		customer.addTicket(ticket);
		this.customerRepository.save(customer);
        this.policyManagement.increaseAllPoliciesPremiumBy(INCREASE_PREMIUM, customerId);
	}
    
}
