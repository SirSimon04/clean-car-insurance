package de.sri.application.premiumcalculator;

public class StandardPremiumCalculationStrategy implements PremiumCalculationStrategy {
	@Override
	public double calculatePremium(double carValue) {
		return carValue * 0.1;
	}
}
