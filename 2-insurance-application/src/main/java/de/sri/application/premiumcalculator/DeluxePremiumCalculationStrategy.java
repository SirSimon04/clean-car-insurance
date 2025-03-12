package de.sri.application.premiumcalculator;

public class DeluxePremiumCalculationStrategy implements PremiumCalculationStrategy {
	@Override
	public double calculatePremium(double carValue) {
		return carValue * 0.15;
	}
}
