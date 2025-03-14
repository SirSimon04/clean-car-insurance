package de.sri.domain.usecases;

import de.sri.domain.entities.Policy;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.CustomerTooYoungException;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.exceptions.CarTooExpensiveException;

public interface PolicyManagement {
    void addPolicyToCustomer(int customerId, Policy policy) throws CustomerNotFoundException, CustomerTooYoungException,
            CarTooExpensiveException, InvalidPremiumAmountException, PropertyNotNullException;

    void removePolicyFromCustomer(int customerId, Policy policy)
            throws CustomerNotFoundException, CustomerTooYoungException;

    void increaseAllPoliciesPremiumBy(double value, int customerId)
            throws CustomerNotFoundException, InvalidPremiumAmountException, PropertyNotNullException;
}
