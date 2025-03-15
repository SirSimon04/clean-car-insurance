package de.sri.domain.usecases;

import de.sri.domain.entities.Ticket;
import de.sri.domain.exceptions.BaseDomainException;

public interface TicketManagement {

    void createTicketForCustomer(int customerId, Ticket ticket) throws BaseDomainException;
}
