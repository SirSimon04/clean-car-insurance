package de.sri.usescases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.application.usecases.TicketManagementImpl;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Ticket;
import de.sri.domain.exceptions.BaseDomainException;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.directors.TestCustomerDirector;

public class TicketManagementImplTest {

    private CustomerRepository customerRepository;
    private TicketManagementImpl ticketManagement;
    private PolicyManagementImpl policyManagementImpl;

    public TicketManagementImplTest() throws BaseDomainException {
        this.customerRepository = new CustomerRepositoryImpl();
        this.policyManagementImpl = new PolicyManagementImpl(customerRepository);
        this.ticketManagement = new TicketManagementImpl(customerRepository, policyManagementImpl);
    }

    @Test
    void create_ticket_for_customer() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        customerRepository.save(customer);

        Ticket ticket = new Ticket(2, LocalDate.now(), 10.0);
        ticketManagement.createTicketForCustomer(1, ticket);

        assertEquals(customerRepository.findById(1).get().getTickets().size(), 1);
        assertEquals(customerRepository.findById(1).get().getTickets().get(0), ticket);
    }

    @Test
    void increase_policy_premium_per_ticket() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        double premium = policy.getPremium().getAmount();

        Ticket ticket = new Ticket(2, LocalDate.now(), 10.0);
        ticketManagement.createTicketForCustomer(1, ticket);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getPremium().getAmount(),
                premium + ticketManagement.INCREASE_PREMIUM);
    }

    @Test
    void decline_policy_when_ticket_amount_reaches_5() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        for (int i = 0; i < 5; i++) {
            Ticket ticket = new Ticket(i, LocalDate.now(), 10.0);
            ticketManagement.createTicketForCustomer(1, ticket);
        }

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

    @Test
    void increase_all_policy_premiums_based_on_highest_carValue_when_speeding_over_20() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        customerRepository.save(customer);

        Policy policy1 = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        Policy policy2 = new Policy(2, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000);
        policyManagementImpl.addPolicyToCustomer(1, policy1);
        policyManagementImpl.addPolicyToCustomer(1, policy2);

        double premium1 = policy1.getPremium().getAmount();
        double premium2 = policy2.getPremium().getAmount();

        Ticket ticket = new Ticket(1, LocalDate.now(), 21.0);
        ticketManagement.createTicketForCustomer(1, ticket);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getPremium().getAmount(),
                premium1 + ticketManagement.INCREASE_PREMIUM + 1000);
        assertEquals(customerRepository.findById(1).get().getPolicies().get(1).getPremium().getAmount(),
                premium2 + ticketManagement.INCREASE_PREMIUM + 1000);
    }

    @Test
    void decline_all_policy_premiums_when_speeding_over_50() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        customerRepository.save(customer);

        Policy policy1 = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        Policy policy2 = new Policy(2, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000);
        policyManagementImpl.addPolicyToCustomer(1, policy1);
        policyManagementImpl.addPolicyToCustomer(1, policy2);

        Ticket ticket = new Ticket(1, LocalDate.now(), 51.0);
        ticketManagement.createTicketForCustomer(1, ticket);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
        assertEquals(customerRepository.findById(1).get().getPolicies().get(1).getStatus(), PolicyStatus.DECLINED);
    }
}
