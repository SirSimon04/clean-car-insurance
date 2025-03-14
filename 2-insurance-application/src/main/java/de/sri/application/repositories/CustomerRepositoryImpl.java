package de.sri.application.repositories;

import de.sri.domain.entities.Accident;
import de.sri.domain.entities.Customer;
import de.sri.domain.entities.Policy;
import de.sri.domain.entities.PolicyProgram;
import de.sri.domain.entities.PolicyStatus;
import de.sri.domain.entities.Ticket;
import de.sri.domain.exceptions.CustomerNotFoundException;
import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.repositories.CustomerRepository;
import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.EmailAddress;
import de.sri.domain.valueobjects.PersonName;
import de.sri.domain.directors.CustomerDirector;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<Integer, Customer> customers = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public CustomerRepositoryImpl() throws PropertyNotNullException, InvalidEmailAddress {
        this.addSampleData();
    }

    @Override
    public Customer save(Customer customer) throws PropertyNotNullException, InvalidEmailAddress {
        customer = this.checkAndSetIds(customer);
        this.customers.remove(customer.getId());
        this.customers.put(customer.getId(), customer);
        System.out.println("Customer saved: " + customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void delete(int id) throws CustomerNotFoundException {
        if (!customers.containsKey(id)) {
            throw new CustomerNotFoundException(id);
        }
        customers.remove(id);
    }

    @Override
    public List<Customer> findByPolicyStatus(PolicyStatus status) {
        return customers.values().stream()
                .filter(customer -> customer.getPolicies().stream().anyMatch(policy -> policy.getStatus() == status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByAccidentCostGreaterThan(double cost) {
        return customers.values().stream()
                .filter(customer -> customer.getAccidents().stream().anyMatch(accident -> accident.getCost() > cost))
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> findByTicketSpeedExcessGreaterThan(double speedExcess) {
        return customers.values().stream().filter(
                customer -> customer.getTickets().stream().anyMatch(ticket -> ticket.getSpeedExcess() > speedExcess))
                .collect(Collectors.toList());
    }

    private Customer checkAndSetIds(Customer customer) throws PropertyNotNullException, InvalidEmailAddress {
        if (customer.getId() == 0) {
            int newId = idGenerator.incrementAndGet();
            customer = new CustomerDirector(new Customer.Builder()).buildNewFromObject(newId, customer);
        }
        for (Policy policy : customer.getPolicies()) {
            if (policy.getId() == 0) {
                policy.setId(idGenerator.incrementAndGet());
            }
        }
        for (Accident accident : customer.getAccidents()) {
            if (accident.getId() == 0) {
                customer.removeAccident(accident.getId());
                accident = new Accident(idGenerator.incrementAndGet(), accident.getCost(), accident.getDate(),
                        customer.getId());
                customer.addAccident(accident);
            }
        }
        for (Ticket ticket : customer.getTickets()) {
            if (ticket.getId() == 0) {
                customer.removeTicket(ticket.getId());
                ticket = new Ticket(idGenerator.incrementAndGet(), ticket.getDate(), ticket.getSpeedExcess());
                customer.addTicket(ticket);
            }
        }
        customers.put(customer.getId(), customer);

        return customer;
    }

    private void addSampleData() throws PropertyNotNullException, InvalidEmailAddress {
        Address address1 = new Address("Musterstraße 1", "Berlin", "BE", "10115", "Deutschland");
        Address address2 = new Address("Hauptstraße 23", "München", "BY", "80331", "Deutschland");

        Customer customer1 = new CustomerDirector(new Customer.Builder()).buildNew(idGenerator.incrementAndGet(),
                new PersonName("Max", "Mustermann"), LocalDate.of(1990, 1, 10),
                new EmailAddress("max.mustermann@example.de"), address1);
        Customer customer2 = new CustomerDirector(new Customer.Builder()).buildNew(idGenerator.incrementAndGet(),
                new PersonName("Erika", "Mustermann"), LocalDate.of(1995, 2, 20),
                new EmailAddress("erika.mustermann@example.de"), address2);

        Policy policy1 = new Policy(idGenerator.incrementAndGet(), PolicyStatus.ACTIVE, PolicyProgram.STANDARD,
                25000.0);
        Policy policy2 = new Policy(idGenerator.incrementAndGet(), PolicyStatus.INACTIVE, PolicyProgram.BASIC, 18000.0);

        customer1.addPolicy(policy1);
        customer2.addPolicy(policy2);

        Accident accident1 = new Accident(idGenerator.incrementAndGet(), 3000.0, LocalDate.of(2025, 1, 5),
                policy1.getId());
        customer1.addAccident(accident1);

        Ticket ticket1 = new Ticket(idGenerator.incrementAndGet(), LocalDate.of(2025, 1, 10), 20.0);
        customer2.addTicket(ticket1);

        save(customer1);
        save(customer2);

    }
}
