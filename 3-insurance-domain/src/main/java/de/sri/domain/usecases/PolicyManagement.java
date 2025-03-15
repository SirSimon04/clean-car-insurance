package de.sri.domain.usecases;

import de.sri.domain.entities.Policy;
import de.sri.domain.exceptions.BaseDomainException;

public interface PolicyManagement {
    void addPolicyToCustomer(int customerId, Policy policy) throws BaseDomainException;

    void removePolicyFromCustomer(int customerId, Policy policy) throws BaseDomainException;

    void increaseAllPoliciesPremiumBy(double value, int customerId) throws BaseDomainException;
}
