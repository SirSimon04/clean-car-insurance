package de.sri.application.usecases;

import de.sri.domain.entities.Customer;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Ticket;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.usecases.TicketManagement;
import de.sri.domain.exceptions.BaseDomainException;
import de.sri.domain.exceptions.CustomerNotFoundException;

public class TicketManagementImpl implements TicketManagement {

    public final int INCREASE_PREMIUM = 10;

    private final CustomerRepository customerRepository;
    private final PolicyManagement policyManagement;

    public TicketManagementImpl(CustomerRepository customerRepository, PolicyManagement policyManagement) {
        this.customerRepository = customerRepository;
        this.policyManagement = policyManagement;
    }

    private Customer getCustomer(int customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public void createTicketForCustomer(int customerId, Ticket ticket) throws BaseDomainException {
        Customer customer = getCustomer(customerId);
        customer.addTicket(ticket);
        if (customer.getTickets().size() >= 5) {
            // Too many tickets
            customer.getPolicies().forEach(policy -> {
                // policy.setPremium(policy.getPremium().getAmount() + INCREASE_PREMIUM);
                policy.setStatus(PolicyStatus.DECLINED);
            });
        }

        final double[] increasePremiumValue = { INCREASE_PREMIUM };
        if (ticket.getSpeedExcess() > 20 && ticket.getSpeedExcess() <= 50) {
            customer.getPolicies().stream()
                    .max((p1, p2) -> Double.compare(p1.getPremium().getAmount(), p2.getPremium().getAmount()))
                    .ifPresent(policy -> {
                        increasePremiumValue[0] = INCREASE_PREMIUM + policy.getCarValue() * 0.02;
                    });
        } else if (ticket.getSpeedExcess() > 50) {
            customer.getPolicies().forEach(policy -> {
                // policy.setPremium(policy.getPremium().getAmount() + INCREASE_PREMIUM);
                policy.setStatus(PolicyStatus.DECLINED);
            });
        }
        this.customerRepository.save(customer);
        this.policyManagement.increaseAllPoliciesPremiumBy(increasePremiumValue[0], customerId);
    }

}
