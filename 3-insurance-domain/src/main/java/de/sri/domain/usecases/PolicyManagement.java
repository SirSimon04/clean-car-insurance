package de.sri.domain.usecases;

import de.sri.domain.entities.Policy;
import de.sri.domain.exceptions.CustomerNotFoundException;

public interface PolicyManagement {
	void addPolicyToCustomer(int customerId, Policy policy) throws CustomerNotFoundException;

	void removePolicyFromCustomer(int customerId, Policy policy) throws CustomerNotFoundException;

	void increaseAllPoliciesPremiumBy(double value, int customerId) throws CustomerNotFoundException;
}
