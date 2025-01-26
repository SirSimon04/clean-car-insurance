package de.sri.domain.entities;

import java.time.LocalDate;

public class Ticket {
	private final int id;
	private LocalDate date;
	private double speedExcess;
	private final int customerId;

	public Ticket(int id, LocalDate date, double speedExcess, int customerId) {
		this.id = id;
		this.date = date;
		this.speedExcess = speedExcess;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getSpeedExcess() {
		return speedExcess;
	}

	public void setSpeedExcess(double speedExcess) {
		this.speedExcess = speedExcess;
	}

	public int getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"id=" + id +
				", date=" + date +
				", speedExcess=" + speedExcess +
				", customerId=" + customerId +
				'}';
	}
}
