package de.sri.domain.usecases;

import de.sri.domain.entities.Policy;

public interface PolicyManagement {
    void addPolicyToCustomer(int customerId, Policy policy);

	void removePolicyFromCustomer(int customerId, Policy policy);

    void increaseAllPoliciesPremiumBy(int value, int customerId);
}