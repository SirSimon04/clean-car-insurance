package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.valueobjects.Premium;

public class PolicyManagementImpl implements PolicyManagement{

	//REVISIT: Should the repository be injected here?
    private final CustomerRepository customerRepository;

    public PolicyManagementImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

	private Customer getCustomer(int customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

    @Override
	public void addPolicyToCustomer(int customerId, Policy policy) {		
		Customer customer = getCustomer(customerId);
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
		customerRepository.save(customer);
	}

	@Override
	public void removePolicyFromCustomer(int customerId, Policy policy) {
		Customer customer = getCustomer(customerId);
		customer.removePolicy(policy.getId());
		customerRepository.save(customer);
	}

	@Override
	public void increaseAllPoliciesPremiumBy(int value, int customerId) {
		Customer customer = getCustomer(customerId);

		customer.getPolicies().forEach(policy -> {
			double newPremium = policy.getPremium().getAmount() + value;
			policy.setPremium(new Premium(newPremium, policy.getPremium().getCurrency()));
		});

		customerRepository.save(customer);
	}
}
