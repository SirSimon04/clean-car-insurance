package de.sri.domain.usecases;

import de.sri.domain.entities.Ticket;

public interface TicketManagement {
    
    void createTicketForCustomer(int customerId, Ticket ticket);
} 