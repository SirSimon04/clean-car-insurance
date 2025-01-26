package de.sri.domain.entities;

import java.time.LocalDate;

public class Accident {
	private final int id;
	private double cost;
	private LocalDate date;
	private final int customerId;

	public Accident(int id, double cost, LocalDate date, int customerId) {
		this.id = id;
		this.cost = cost;
		this.date = date;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "Accident{" +
				"id=" + id +
				", cost=" + cost +
				", date=" + date +
				", customerId=" + customerId +
				'}';
	}
}
