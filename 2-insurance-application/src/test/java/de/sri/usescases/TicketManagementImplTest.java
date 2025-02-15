package de.sri.usescases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.application.usecases.TicketManagementImpl;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Ticket;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.valueobjects.Address;

public class TicketManagementImplTest {

    private CustomerRepository customerRepository;
    private TicketManagementImpl ticketManagement;
    private PolicyManagementImpl policyManagementImpl;

    public TicketManagementImplTest() {
        this.customerRepository = new CustomerRepositoryImpl();
        this.policyManagementImpl = new PolicyManagementImpl(customerRepository);
        this.ticketManagement = new TicketManagementImpl(customerRepository, policyManagementImpl);
    }


    @Test
    void create_ticket_for_customer() {
        Customer customer = new Customer(100, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Ticket ticket = new Ticket(2, LocalDate.now(), 10.0, 100);
        ticketManagement.createTicketForCustomer(100, ticket);

        assertEquals(customerRepository.findById(100).get().getTickets().size(), 1);
        assertEquals(customerRepository.findById(100).get().getTickets().get(0), ticket);
    }

    @Test
    void create_ticket_for_customer_increase_policy(){
        Customer customer = new Customer(101, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
        policyManagementImpl.addPolicyToCustomer(101, policy);

        double premium = policy.getPremium().getAmount();

        Ticket ticket = new Ticket(2, LocalDate.now(), 10.0, 101);
        ticketManagement.createTicketForCustomer(101, ticket);

        assertEquals(customerRepository.findById(101).get().getPolicies().get(0).getPremium().getAmount(), premium + ticketManagement.INCREASE_PREMIUM);        
    }

    
}
