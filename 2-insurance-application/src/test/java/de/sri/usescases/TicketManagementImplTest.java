package de.sri.usescases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.application.usecases.TicketManagementImpl;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Ticket;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.usecases.PolicyManagement;
import de.sri.domain.valueobjects.Address;

@ExtendWith(MockitoExtension.class)
public class TicketManagementImplTest {

    @Mock
    private CustomerManagement customerManagement;

    @InjectMocks
    private TicketManagementImpl ticketManagement;
    
    @InjectMocks
    private PolicyManagementImpl policyManagementImpl;

    @Mock
    private PolicyManagement policyManagement;


    @Test
    void create_ticket_for_customer() {
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerManagement.getCustomer(1)).thenReturn(customer);

        Ticket ticket = new Ticket(2, LocalDate.now(), 10.0, 1);
        ticketManagement.createTicketForCustomer(1, ticket);

        assertEquals(customerManagement.getCustomer(1).getTickets().size(), 1);
        assertEquals(customerManagement.getCustomer(1).getTickets().get(0), ticket);
    }

    // @Test
    // void create_ticket_for_customer_increase_policy(){
    //     Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
    //     when(customerManagement.getCustomer(1)).thenReturn(customer);

    //     Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
    //     policyManagementImpl.addPolicyToCustomer(1, policy);

    //     double premium = policy.getPremium().getAmount();

    //     Ticket ticket = new Ticket(2, LocalDate.now(), 10.0, 1);
    //     ticketManagement.createTicketForCustomer(1, ticket);

    //     assertEquals(customerManagement.getCustomer(1).getPolicies().get(0).getPremium().getAmount(), premium + 10.0);        
    // }

    
}
