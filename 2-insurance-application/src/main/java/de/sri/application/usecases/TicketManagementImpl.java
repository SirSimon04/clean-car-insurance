package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Ticket;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.usecases.TicketManagement;

public class TicketManagementImpl implements TicketManagement {

    private final CustomerManagement customerManagement;

    public TicketManagementImpl(CustomerManagement customerManagement) {
        this.customerManagement = customerManagement;
    }

    @Override
	public void createTicketForCustomer(int customerId, Ticket ticket) {
		Customer customer = this.customerManagement.getCustomer(customerId);
		customer.addTicket(ticket);
		this.customerManagement.updateCustomer(customer);
	}
    
}
