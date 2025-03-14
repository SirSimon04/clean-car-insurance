package de.sri.domain.usecases;

import de.sri.domain.entities.Ticket;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;

public interface TicketManagement {

    void createTicketForCustomer(int customerId, Ticket ticket) throws CustomerNotFoundException,
            InvalidPremiumAmountException, PropertyNotNullException, PropertyNotNullException, InvalidEmailAddress;
}
