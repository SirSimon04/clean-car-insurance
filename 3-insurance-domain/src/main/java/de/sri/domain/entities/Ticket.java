package de.sri.domain.entities;

import java.time.LocalDate;

public class Ticket {
	private final int id;
	private LocalDate date;
	private double speedExcess;

	public Ticket(int id, LocalDate date, double speedExcess) {
		this.id = id;
		this.date = date;
		this.speedExcess = speedExcess;
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

	@Override
	public String toString() {
		return "Ticket{" +
				"id=" + id +
				", date=" + date +
				", speedExcess=" + speedExcess +
				'}';
	}
}
