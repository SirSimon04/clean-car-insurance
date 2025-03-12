package de.sri.repositories;

import de.sri.application.repositories.CustomerRepositoryImpl;
import de.sri.domain.entities.*;
import de.sri.domain.valueobjects.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryImplTest {

    private CustomerRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new CustomerRepositoryImpl();
    }

    @Test
    void testSave() {
        // Arrange
        Address address = new Address("Teststraße 42", "Hamburg", "HH", "20457", "Deutschland");
        Customer customer = new Customer(0, "Anna", "Schmidt",
                LocalDate.of(1987, 6, 15), "anna.schmidt@example.de", address);

        // Act
        Customer savedCustomer = repository.save(customer);

        // Assert
        assertNotNull(savedCustomer.getId());
        assertEquals("Anna", savedCustomer.getFirstName());
        assertTrue(repository.findById(savedCustomer.getId()).isPresent());
    }

    @Test
    void testFindById() {
        // Arrange
        int existingCustomerId = 1;

        // Act
        Optional<Customer> customer = repository.findById(existingCustomerId);

        // Assert
        assertTrue(customer.isPresent());
        assertEquals("Max", customer.get().getFirstName());
    }

    @Test
    void testFindAll() {
        // Act
        List<Customer> customers = repository.findAll();

        // Assert
        assertEquals(2, customers.size());
    }

    @Test
    void testDelete() throws Exception {
        // Arrange
        int existingCustomerId = 1;

        // Act
        repository.delete(existingCustomerId);

        // Assert
        assertFalse(repository.findById(existingCustomerId).isPresent());
    }

    @Test
    void testFindByPolicyStatus() {
        // Arrange
        PolicyStatus status = PolicyStatus.ACTIVE;

        // Act
        List<Customer> customers = repository.findByPolicyStatus(status);

        // Assert
        assertEquals(1, customers.size());
        assertEquals("Max", customers.get(0).getFirstName());
    }

    @Test
    void testFindByAccidentCostGreaterThan() {
        // Arrange
        double costThreshold = 2000.0;

        // Act
        List<Customer> customers = repository.findByAccidentCostGreaterThan(costThreshold);

        // Assert
        assertEquals(1, customers.size());
        assertEquals("Max", customers.get(0).getFirstName());
    }

    @Test
    void testFindByTicketSpeedExcessGreaterThan() {
        // Arrange
        double speedExcessThreshold = 10.0;

        // Act
        List<Customer> customers = repository.findByTicketSpeedExcessGreaterThan(speedExcessThreshold);

        // Assert
        assertEquals(1, customers.size());
        assertEquals("Erika", customers.get(0).getFirstName());
    }

    @Test
    void testSavePolicy() {
        // Arrange
        Customer customer = this.repository.findById(1).get();
        Policy policy = new Policy(0, PolicyStatus.ACTIVE, PolicyProgram.DELUXE,
                30000.0, customer.getId());

        // Act
        customer.addPolicy(policy);
        Customer savedCustomer = repository.save(customer);

        // Assert
        assertNotNull(savedCustomer.getId());
        assertEquals(2, this.repository.findById(savedCustomer.getId()).get().getPolicies().size());
    }
}
