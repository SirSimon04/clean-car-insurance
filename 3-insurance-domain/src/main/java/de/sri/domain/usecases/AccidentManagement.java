package de.sri.domain.usecases;

import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PolicyNotFoundException;
import de.sri.domain.exceptions.PropertyNotNullException;

public interface AccidentManagement {
    void createAccidentForCustomer(int customerId, Accident accident) throws CustomerNotFoundException,
            PolicyNotFoundException, InvalidPremiumAmountException, PropertyNotNullException;
}
