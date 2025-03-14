package de.sri.domain.usecases;

import de.sri.domain.entities.Ticket;
import de.sri.domain.exceptions.CustomerNotFoundException;

public interface TicketManagement {

    void createTicketForCustomer(int customerId, Ticket ticket) throws CustomerNotFoundException;
}
