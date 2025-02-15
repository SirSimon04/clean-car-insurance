package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.valueobjects.Premium;

public class PolicyManagementImpl implements PolicyManagement{

	//REVISIT: Should the repository be injected here?
    private final CustomerManagement customerManagement;

    public PolicyManagementImpl(CustomerManagement customerManagement) {
        this.customerManagement = customerManagement;
    }

    @Override
	public void addPolicyToCustomer(int customerId, Policy policy) {		
		Customer customer = this.customerManagement.getCustomer(customerId);
		// Customers has to be 18 years old to craete a policy
		if (customer.getAge() < 18) {
			throw new IllegalArgumentException("Customer has to be 18 years old to create a policy!");
		}

		// Car value cannot be more than 100.000
		if(policy.getCarValue() > 100000) {
			throw new IllegalArgumentException("Car value cannot be more than 100,000!");
		}

		// Calculate Premium price for the Customer
		PolicyProgram program = policy.getProgram();
		double premiumPrice = 0;
		switch (program) {
			case BASIC:
				premiumPrice += policy.getCarValue() * 0.05;
				break;
			case STANDARD:
				premiumPrice += policy.getCarValue() * 0.1;
				break;
			case DELUXE:
				premiumPrice += policy.getCarValue() * 0.15;
				break;		
			default:
				break;
		}
		
		// Check if young or senior driver fee should be added
		if(customer.getAge() < 21 || customer.getAge() > 80) {
			premiumPrice += premiumPrice * 0.1;
		}

		// Check if car value is more than 40.000
		if(policy.getCarValue() > 40000) {
			premiumPrice += policy.getCarValue() * 0.01;
		}

		policy.setPremium(new Premium(premiumPrice, "EUR")); // REVISIT: Currency should be a constant
		customer.addPolicy(policy);
		this.customerManagement.updateCustomer(customer);
	}

	@Override
	public void removePolicyFromCustomer(int customerId, Policy policy) {
		Customer customer = this.customerManagement.getCustomer(customerId);
		customer.removePolicy(policy.getId());
		this.customerManagement.updateCustomer(customer);
	}

	@Override
	public void increaseAllPoliciesPremiumBy(int value, int customerId) {
		Customer customer = this.customerManagement.getCustomer(customerId);

		customer.getPolicies().forEach(policy -> {
			double newPremium = policy.getPremium().getAmount() + value;
			policy.setPremium(new Premium(newPremium, policy.getPremium().getCurrency()));
		});
		
		this.customerManagement.updateCustomer(customer);
	}
}
