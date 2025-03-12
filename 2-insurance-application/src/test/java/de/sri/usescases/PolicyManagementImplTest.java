package de.sri.usescases;

import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.directors.TestCustomerDirector;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PolicyManagementImplTest {

        @Mock
        private CustomerRepository customerRepository;

        @InjectMocks
        private PolicyManagementImpl policyManagement;

        @Test
        void add_basic_policy() throws CustomerNotFoundException {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);

                // Call the addPolicyToCustomer method
                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(500, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));
                //
        }

        @Test
        void add_policy_basic_with_young_driver() throws CustomerNotFoundException {
                // Create a customer with age < 18
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createYoungDriver();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);

                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(550, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));
                //
        }

        @Test
        void add_policy_basic_with_senior_driver() throws CustomerNotFoundException {
                // Create a customer with age > 80
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createSeniorDriver();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);

                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(550, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));

        }

        @Test
        void add_basic_policy_with_car_value_fee() throws CustomerNotFoundException {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 50000, 1);

                // Call the addPolicyToCustomer method
                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(3000, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));
        }

        @Test
        void add_standard_policy() throws CustomerNotFoundException {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.STANDARD, 10000, 1);

                // Call the addPolicyToCustomer method
                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(1000, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));

        }

        @Test
        void add_deluxe_policy() throws CustomerNotFoundException {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.DELUXE, 10000, 1);

                // Call the addPolicyToCustomer method
                policyManagement.addPolicyToCustomer(1, policy);

                assertEquals(1500, policy.getPremium().getAmount());
                assertEquals(1, customer.getPolicies().size());
                assertEquals(policy, customer.getPolicies().get(0));

        }

        @Test
        void add_policy_with_too_high_car_value() {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 120000, 1);

                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> policyManagement.addPolicyToCustomer(1, policy));
                assertEquals("Car value cannot be more than 100,000!", exception.getMessage());
        }

        @Test
        void add_policy_with_customer_under_18_years_old() {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createDriverUnder18();
                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 120000, 1);

                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> policyManagement.addPolicyToCustomer(1, policy));
                assertEquals("Customer has to be 18 years old to create a policy!", exception.getMessage());
        }

        @Test
        void increase_all_policies_premium() throws CustomerNotFoundException {
                Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();

                when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

                Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 40000, 1);
                policyManagement.addPolicyToCustomer(1, policy);

                double premium = policy.getPremium().getAmount();
                policyManagement.increaseAllPoliciesPremiumBy(10, customer.getId());
                assertEquals(policy.getPremium().getAmount(), premium + 10);
        }
}
