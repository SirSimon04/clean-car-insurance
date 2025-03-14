package de.sri.domain.usecases;

import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.PolicyNotFoundException;

public interface AccidentManagement {
    void createAccidentForCustomer(int customerId, Accident accident)
            throws CustomerNotFoundException, PolicyNotFoundException;
}
