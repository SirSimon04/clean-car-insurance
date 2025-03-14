package de.sri.application.premiumcalculator;

public class DeluxePremiumCalculationStrategy implements PremiumCalculationStrategy {
	private double percentage = 0.15;

	@Override
	public double calculatePremium(double carValue) {
		return carValue * percentage;
	}
}
