package de.sri.domain.usecases;

import de.sri.domain.entities.Accident;
import de.sri.domain.exceptions.BaseDomainException;

public interface AccidentManagement {
    void createAccidentForCustomer(int customerId, Accident accident) throws BaseDomainException;
}
