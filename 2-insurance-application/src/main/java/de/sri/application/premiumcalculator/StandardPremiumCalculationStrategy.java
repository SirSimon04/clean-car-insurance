package de.sri.application.premiumcalculator;

public class StandardPremiumCalculationStrategy implements PremiumCalculationStrategy {

	private double percentage = 0.1;

	@Override
	public double calculatePremium(double carValue) {
		return carValue * this.percentage;
	}
}
