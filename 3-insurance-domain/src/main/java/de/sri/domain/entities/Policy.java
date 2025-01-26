package de.sri.domain.entities;

import de.sri.domain.valueobjects.Premium;

public class Policy {
	private final int id;
	private PolicyStatus status;
	private PolicyProgram program;
	private double carValue;
	private Premium premium;
	private final int customerId;

	public Policy(int id, PolicyStatus status, PolicyProgram program, double carValue, Premium premium,
			int customerId) {
		this.id = id;
		this.status = status;
		this.program = program;
		this.carValue = carValue;
		this.premium = premium;
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public PolicyStatus getStatus() {
		return status;
	}

	public void setStatus(PolicyStatus status) {
		this.status = status;
	}

	public PolicyProgram getProgram() {
		return program;
	}

	public void setProgram(PolicyProgram program) {
		this.program = program;
	}

	public double getCarValue() {
		return carValue;
	}

	public void setCarValue(double carValue) {
		this.carValue = carValue;
	}

	public Premium getPremium() {
		return premium;
	}

	public void setPremium(Premium premium) {
		this.premium = premium;
	}

	public int getCustomerId() {
		return customerId;
	}

	@Override
	public String toString() {
		return "Policy{" +
				"id=" + id +
				", status=" + status +
				", program=" + program +
				", carValue=" + carValue +
				", premium=" + premium +
				", customerId=" + customerId +
				'}';
	}
}
