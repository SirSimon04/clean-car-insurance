package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.valueobjects.Premium;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.CustomerTooYoungException;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.exceptions.CarTooExpensiveException;
import de.sri.application.premiumcalculator.PremiumCalculationStrategyFactory;
import de.sri.application.premiumcalculator.PremiumCalculationStrategy;

public class PolicyManagementImpl implements PolicyManagement {

    private final CustomerRepository customerRepository;

    public PolicyManagementImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Customer getCustomer(int customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public void addPolicyToCustomer(int customerId, Policy policy)
            throws CustomerTooYoungException, CustomerNotFoundException, CarTooExpensiveException,
            InvalidPremiumAmountException, PropertyNotNullException, InvalidEmailAddress {
        Customer customer = getCustomer(customerId);
        // Customers has to be 18 years old to craete a policy
        if (customer.getAge() < 18) {
            throw new CustomerTooYoungException();
        }

        // Car value cannot be more than 100.000
        if (policy.getCarValue() > 100000) {
            throw new CarTooExpensiveException(100000);
        }

        // Calculate Premium price for the Customer
        PremiumCalculationStrategy strategy = PremiumCalculationStrategyFactory.getStrategy(policy.getProgram());

        double premiumPrice = strategy.calculatePremium(policy.getCarValue());

        // Check if young or senior driver fee should be added
        if (customer.getAge() < 21 || customer.getAge() > 80) {
            premiumPrice += premiumPrice * 0.1;
        }

        // Check if car value is more than 40.000
        if (policy.getCarValue() > 40000) {
            premiumPrice += policy.getCarValue() * 0.01;
        }

        policy.setPremium(new Premium(premiumPrice, "EUR")); // REVISIT: Currency should be a constant
        customer.addPolicy(policy);
        customerRepository.save(customer);
    }

    @Override
    public void removePolicyFromCustomer(int customerId, Policy policy)
            throws CustomerNotFoundException, PropertyNotNullException, InvalidEmailAddress {
        Customer customer = getCustomer(customerId);
        customer.removePolicy(policy.getId());
        customerRepository.save(customer);
    }

    @Override
    public void increaseAllPoliciesPremiumBy(double value, int customerId) throws CustomerNotFoundException,
            InvalidPremiumAmountException, PropertyNotNullException, InvalidEmailAddress {
        Customer customer = getCustomer(customerId);

        customer.getPolicies().forEach(policy -> {
            double newPremium = policy.getPremium().getAmount() + value;
            try {
                policy.setPremium(new Premium(newPremium, policy.getPremium().getCurrency()));
            } catch (InvalidPremiumAmountException | PropertyNotNullException e) {
                e.printStackTrace();
            }
        });

        customerRepository.save(customer);
    }
}
