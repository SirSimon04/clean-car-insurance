package de.sri.application.premiumcalculator;

public class BasicPremiumCalculationStrategy implements PremiumCalculationStrategy {
    private double percentage = 0.05;

    @Override
    public double calculatePremium(double carValue) {
        return carValue * this.percentage;
    }
}
