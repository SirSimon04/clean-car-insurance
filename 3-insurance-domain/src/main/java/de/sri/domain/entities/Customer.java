package de.sri.domain.entities;

import de.sri.domain.valueobjects.Address;
import de.sri.domain.valueobjects.EmailAddress;
import de.sri.domain.valueobjects.PersonName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private final int id;
    private PersonName name;
    private LocalDate dateOfBirth;
    private EmailAddress email;
    private Address address;
    private List<Policy> policies;
    private List<Accident> accidents;
    private List<Ticket> tickets;

    private Customer(int id, PersonName name, LocalDate dateOfBirth, EmailAddress email, Address address) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.policies = new ArrayList<>();
        this.accidents = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public PersonName getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        LocalDate now = LocalDate.now();
        int age = now.getYear() - dateOfBirth.getYear();
        if (now.getDayOfYear() < dateOfBirth.getDayOfYear()) {
            age--;
        }
        return age;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public List<Policy> getPolicies() {
        return new ArrayList<>(policies);
    }

    public List<Accident> getAccidents() {
        return new ArrayList<>(accidents);
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    public void addAccident(Accident accident) {
        this.accidents.add(accident);
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(int ticketId) {
        tickets.removeIf(ticket -> ticket.getId() == ticketId);
    }

    public void removePolicy(int policyId) {
        policies.removeIf(policy -> policy.getId() == policyId);
    }

    public void removeAccident(int accidentId) {
        accidents.removeIf(accident -> accident.getId() == accidentId);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName='" + name.getFirstName() + '\'' + ", lastName='"
                + name.getLastName() + '\'' + ", dateOfBirth=" + dateOfBirth + ", email='" + email + '\'' + ", address="
                + address + '}';
    }

    public static class Builder {
        private int id;
        private PersonName name;
        private LocalDate dateOfBirth;
        private EmailAddress email;
        private Address address;
        private List<Policy> policies = new ArrayList<>();
        private List<Accident> accidents = new ArrayList<>();
        private List<Ticket> tickets = new ArrayList<>();

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(PersonName name) {
            this.name = name;
            return this;
        }

        public Builder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder withEmail(EmailAddress email) {
            this.email = email;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder withPolicies(List<Policy> policies) {
            this.policies = policies;
            return this;
        }

        public Builder withAccidents(List<Accident> accidents) {
            this.accidents = accidents;
            return this;
        }

        public Builder withTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer(id, name, dateOfBirth, email, address);
            customer.policies = this.policies;
            customer.accidents = this.accidents;
            customer.tickets = this.tickets;
            return customer;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name)
                && Objects.equals(dateOfBirth, customer.dateOfBirth) && Objects.equals(email, customer.email)
                && Objects.equals(address, customer.address) && Objects.equals(policies, customer.policies)
                && Objects.equals(accidents, customer.accidents) && Objects.equals(tickets, customer.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, email, address, policies, accidents, tickets);
    }
}
