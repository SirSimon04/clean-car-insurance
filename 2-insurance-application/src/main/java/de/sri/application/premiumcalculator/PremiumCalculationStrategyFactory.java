package de.sri.application.premiumcalculator;

import de.sri.domain.entities.PolicyProgram;

public class PremiumCalculationStrategyFactory {
    public static PremiumCalculationStrategy getStrategy(PolicyProgram program) {
        switch (program) {
            case BASIC:
                return new BasicPremiumCalculationStrategy();
            case STANDARD:
                return new StandardPremiumCalculationStrategy();
            case DELUXE:
                return new DeluxePremiumCalculationStrategy();
            default:
                throw new IllegalArgumentException("Unknown policy program: " + program);
        }
    }
}
