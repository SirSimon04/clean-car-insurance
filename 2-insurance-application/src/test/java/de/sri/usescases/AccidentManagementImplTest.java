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

import de.sri.application.usecases.AccidentManagementImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.exceptions.BaseDomainException;
import de.sri.domain.directors.TestCustomerDirector;

@ExtendWith(MockitoExtension.class)
public class AccidentManagementImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccidentManagementImpl accidentManagement;

    @InjectMocks
    private PolicyManagementImpl policyManagementImpl;

    @Test
    void create_accident_for_customer() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        Accident accident = new Accident(2, 10000.0, LocalDate.now(), 1);
        accidentManagement.createAccidentForCustomer(1, accident);

        assertEquals(customerRepository.findById(1).get().getAccidents().size(), 1);
        assertEquals(customerRepository.findById(1).get().getAccidents().get(0), accident);
    }

    @Test
    void increase_policy_premium_per_accident() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        double premium = policy.getPremium().getAmount();

        Accident accident = new Accident(2, 10000.0, LocalDate.now(), 1);
        accidentManagement.createAccidentForCustomer(1, accident);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getPremium().getAmount(),
                premium + accidentManagement.INCREASE_PREMIUM);
    }

    @Test
    void decline_policy_when_accidents_amount_reaches_3() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        for (int i = 0; i < 3; i++) {
            Accident accident = new Accident(i, 10000.0, LocalDate.now(), 1);
            accidentManagement.createAccidentForCustomer(1, accident);
        }

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

    @Test
    void not_decline_policy_when_accident_cost_is_under_60000() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        Accident accident = new Accident(2, 50000.0, LocalDate.now(), 1);
        accidentManagement.createAccidentForCustomer(1, accident);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.ACTIVE);
    }

    @Test
    void decline_policy_when_accident_cost_is_over_60000() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        Accident accident = new Accident(2, 61000.0, LocalDate.now(), 1);
        accidentManagement.createAccidentForCustomer(1, accident);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

    @Test
    void decline_policy_when_accident_cost_is_over_car_value() throws BaseDomainException {
        Customer customer = new TestCustomerDirector(new Customer.Builder()).createMockUser();
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000);
        policyManagementImpl.addPolicyToCustomer(1, policy);

        Accident accident = new Accident(2, 110000.0, LocalDate.now(), 1);
        accidentManagement.createAccidentForCustomer(1, accident);

        assertEquals(customerRepository.findById(1).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

}
