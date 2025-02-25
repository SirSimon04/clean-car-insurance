package de.sri.domain.usecases;

import de.sri.domain.entities.Accident;

public interface AccidentManagement {
    void createAccidentForCustomer(int customerId, Accident accident);
}
