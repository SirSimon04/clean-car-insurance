package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Ticket;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;

public class TicketManagementImpl implements TicketManagement {

    private final CustomerManagement customerManagement;
    private final PolicyManagement policyManagement;

    public TicketManagementImpl(CustomerManagement customerManagement, PolicyManagement policyManagement) {
        this.customerManagement = customerManagement;
        this.policyManagement = policyManagement;
    }

    @Override
	public void createTicketForCustomer(int customerId, Ticket ticket) {
		Customer customer = this.customerManagement.getCustomer(customerId);
		customer.addTicket(ticket);
		this.customerManagement.updateCustomer(customer);
        this.policyManagement.increaseAllPoliciesPremiumBy(10, customerId);
	}
    
}
