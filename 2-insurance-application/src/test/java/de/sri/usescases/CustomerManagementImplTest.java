package de.sri.usescases;

import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.application.usecases.CustomerManagementImpl;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.usecases.CustomerManagement;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.Premium;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerManagementImplTest {

    @Mock
    private CustomerRepositoryImpl customerRepository;

    @InjectMocks
    private CustomerManagementImpl customerManagement;

    @Test
    void add_basic_policy() {        
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(500, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void add_policy_basic_with_young_driver() {
        // Create a customer with age < 18
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(20), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
        
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(550, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void add_policy_basic_with_senior_driver() {
        // Create a customer with age > 80
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(85), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
        
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(550, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void add_basic_policy_with_car_value_fee() {        
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000, 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(1000, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void add_standard_policy() {        
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.STANDARD, 10000, 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(1000, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void add_deluxe_policy() {        
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.DELUXE, 10000, 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        assertEquals(1500, policy.getPremium().getAmount());
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testAddPolicyToCustomerCarValueTooHigh() {
        // Create a customer with age > 18
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(20), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(customer);

        // Create a policy with car value > 100000
        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 150000, new Premium(7500, "EUR"), 1);

        // Call the addPolicyToCustomer method and assert that it throws an exception
        assertThrows(IllegalArgumentException.class, () -> customerManagement.addPolicyToCustomer(1, policy));

        // Verify that the customer is not updated in the repository
        verify(customerRepository, never()).update(any());
    }

    @Test
    void testAddPolicyToCustomerYoungDriverFee() {
        // Create a customer with age < 21
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(20), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(customer);

        // Create a policy
        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000, new Premium(2500, "EUR"), 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        // Verify that the premium is calculated correctly with the young driver fee
        assertEquals(2750, policy.getPremium().getAmount()); // 2500 + (2500 * 0.1)

        // Verify that the policy is added to the customer
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));

        // Verify that the customer is updated in the repository
        verify(customerRepository, times(1)).update(customer);
    }

    @Test
    void testAddPolicyToCustomerSeniorDriverFee() {
        // Create a customer with age > 80
        Customer customer = new Customer(1, "John", "Doe", LocalDate.now().minusYears(85), "john.doe@example.com", new Address("Street", "City", "State", "Zip", "Country"));
        when(customerRepository.findById(1)).thenReturn(customer);

        // Create a policy
        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000, new Premium(2500, "EUR"), 1);

        // Call the addPolicyToCustomer method
        customerManagement.addPolicyToCustomer(1, policy);

        // Verify that the premium is calculated correctly with the senior driver fee
        assertEquals(2750, policy.getPremium().getAmount()); // 2500 + (2500 * 0.1)

        // Verify that the policy is added to the customer
        assertEquals(1, customer.getPolicies().size());
        assertEquals(policy, customer.getPolicies().get(0));

        // Verify that the customer is updated in the repository
        verify(customerRepository, times(1)).update(customer);
    }
}