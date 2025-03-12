package de.sri.application.premiumcalculator;

public class BasicPremiumCalculationStrategy implements PremiumCalculationStrategy {
	@Override
	public double calculatePremium(double carValue) {
		return carValue * 0.05;
	}
}
