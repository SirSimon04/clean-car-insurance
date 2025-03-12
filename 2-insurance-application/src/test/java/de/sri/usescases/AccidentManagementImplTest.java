package de.sri.usescases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.application.usecases.AccidentManagementImpl;
import de.sri.application.usecases.PolicyManagementImpl;
import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.valueobjects.Address;

public class AccidentManagementImplTest {
    private CustomerRepository customerRepository;
    private AccidentManagementImpl accidentManagement;
    private PolicyManagementImpl policyManagementImpl;

    public AccidentManagementImplTest() {
        this.customerRepository = new CustomerRepositoryImpl();
        this.accidentManagement = new AccidentManagementImpl(customerRepository);
        this.policyManagementImpl = new PolicyManagementImpl(customerRepository);
    }

    @Test
    void create_accident_for_customer() throws CustomerNotFoundException {
        Customer customer = new Customer(100, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 100);
        policyManagementImpl.addPolicyToCustomer(100, policy);

        Accident accident = new Accident(2, 10000.0, LocalDate.now(), 100, 1);
        accidentManagement.createAccidentForCustomer(100, accident);

        assertEquals(customerRepository.findById(100).get().getAccidents().size(), 1);
        assertEquals(customerRepository.findById(100).get().getAccidents().get(0), accident);
    }

    @Test
    void increase_policy_premium_per_accident() throws CustomerNotFoundException {
        Customer customer = new Customer(101, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
        policyManagementImpl.addPolicyToCustomer(101, policy);

        double premium = policy.getPremium().getAmount();

        Accident accident = new Accident(2, 10000.0, LocalDate.now(), 100, 1);
        accidentManagement.createAccidentForCustomer(101, accident);

        assertEquals(customerRepository.findById(101).get().getPolicies().get(0).getPremium().getAmount(),
                premium + accidentManagement.INCREASE_PREMIUM);
    }

    @Test
    void decline_policy_when_accidents_amount_reaches_3() throws CustomerNotFoundException {
        Customer customer = new Customer(102, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 10000, 1);
        policyManagementImpl.addPolicyToCustomer(102, policy);

        for (int i = 0; i < 3; i++) {
            Accident accident = new Accident(i, 10000.0, LocalDate.now(), 100, 1);
            accidentManagement.createAccidentForCustomer(102, accident);
        }

        assertEquals(customerRepository.findById(102).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

    @Test
    void not_decline_policy_when_accident_cost_is_under_60000() throws CustomerNotFoundException {
        Customer customer = new Customer(102, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000, 1);
        policyManagementImpl.addPolicyToCustomer(102, policy);

        Accident accident = new Accident(2, 50000.0, LocalDate.now(), 100, 1);
        accidentManagement.createAccidentForCustomer(102, accident);

        assertEquals(customerRepository.findById(102).get().getPolicies().get(0).getStatus(), PolicyStatus.ACTIVE);
    }

    @Test
    void decline_policy_when_accident_cost_is_over_60000() throws CustomerNotFoundException {
        Customer customer = new Customer(102, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000, 1);
        policyManagementImpl.addPolicyToCustomer(102, policy);

        Accident accident = new Accident(2, 61000.0, LocalDate.now(), 100, 1);
        accidentManagement.createAccidentForCustomer(102, accident);

        assertEquals(customerRepository.findById(102).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

    @Test
    void decline_policy_when_accident_cost_is_over_car_value() throws CustomerNotFoundException {
        Customer customer = new Customer(102, "John", "Doe", LocalDate.now().minusYears(60), "john.doe@example.com",
                new Address("Street", "City", "State", "Zip", "Country"));
        customerRepository.save(customer);

        Policy policy = new Policy(1, PolicyStatus.ACTIVE, PolicyProgram.BASIC, 100000, 1);
        policyManagementImpl.addPolicyToCustomer(102, policy);

        Accident accident = new Accident(2, 110000.0, LocalDate.now(), 100, 1);
        accidentManagement.createAccidentForCustomer(102, accident);

        assertEquals(customerRepository.findById(102).get().getPolicies().get(0).getStatus(), PolicyStatus.DECLINED);
    }

}
