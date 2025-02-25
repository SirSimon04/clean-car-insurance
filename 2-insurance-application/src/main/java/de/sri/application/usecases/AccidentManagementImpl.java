package de.sri.application.usecases;

import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.AccidentManagement;
import de.sri.domain.valueobjects.Premium;

public class AccidentManagementImpl implements AccidentManagement {

    public final int INCREASE_PREMIUM = 20;

    private final CustomerRepository customerRepository;    

    public AccidentManagementImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Customer getCustomer(int customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Customer not found"));
	}

    @Override
    public void createAccidentForCustomer(int customerId, Accident accident) {
        Customer customer = getCustomer(customerId);           
		customer.addAccident(accident);
        if(customer.getAccidents().size() >= 3) {
            // Too many accidents
            customer.getPolicies().forEach(policy -> {
                policy.setStatus(PolicyStatus.DECLINED);
            });
        }

        Policy policy = customer.getPolicies().stream().filter(p -> p.getId() == accident.getPolicyId()).findFirst().orElseThrow(() -> new IllegalArgumentException("Policy not found"));

        policy.setPremium(
            new Premium(
                policy.getPremium().getAmount() + INCREASE_PREMIUM, 
                policy.getPremium().getCurrency()
            )
        );

        if (accident.getCost() > 60000 || accident.getCost() > policy.getCarValue()) {
            policy.setStatus(PolicyStatus.DECLINED);
        }

        this.customerRepository.save(customer);
    }
    
}
