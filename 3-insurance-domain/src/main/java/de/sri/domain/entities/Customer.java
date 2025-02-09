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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public int getAge(){
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

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
}
