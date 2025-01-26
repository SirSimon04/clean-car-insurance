package de.sri.domain.valueobjects;

public class Premium {
	private final double amount;
	private final String currency;

	public Premium(double amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public String getCurrency() {
		return currency;
	}

	public Premium add(Premium other) {
		if (!this.currency.equals(other.currency)) {
			throw new IllegalArgumentException("Currencies must match");
		}
		return new Premium(this.amount + other.amount, this.currency);
	}

	public Premium subtract(Premium other) {
		if (!this.currency.equals(other.currency)) {
			throw new IllegalArgumentException("Currencies must match");
		}
		return new Premium(this.amount - other.amount, this.currency);
	}

	@Override
	public String toString() {
		return amount + " " + currency;
	}
}
