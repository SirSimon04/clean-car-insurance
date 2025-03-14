package de.sri.domain.entities;

import de.sri.domain.valueobjects.Address;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	private final int id;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String email;
	private Address address;
	private List<Policy> policies;
	private List<Accident> accidents;
	private List<Ticket> tickets;

	public Customer(int id, String firstName, String lastName, LocalDate dateOfBirth, String email,
			Address address) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
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

	public String getEmail() {
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
		return "Customer{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", email='" + email + '\'' +
				", address=" + address +
				'}';
	}

	public static class Builder {
		private int id;
		private String firstName;
		private String lastName;
		private LocalDate dateOfBirth;
		private String email;
		private Address address;
		private List<Policy> policies = new ArrayList<>();
		private List<Accident> accidents = new ArrayList<>();
		private List<Ticket> tickets = new ArrayList<>();

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
			return this;
		}

		public Builder withEmail(String email) {
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
			Customer customer = new Customer(id, firstName, lastName, dateOfBirth, email, address);
			customer.policies = this.policies;
			customer.accidents = this.accidents;
			customer.tickets = this.tickets;
			return customer;
		}
	}
}
