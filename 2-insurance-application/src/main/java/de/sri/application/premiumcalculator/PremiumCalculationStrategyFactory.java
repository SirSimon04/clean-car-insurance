package de.sri.application.premiumcalculator;

import de.sri.domain.entities.PolicyProgram;

import java.util.Map;
import java.util.HashMap;

public class PremiumCalculationStrategyFactory {
    private static final Map<PolicyProgram, PremiumCalculationStrategy> strategies = new HashMap<>();

    static {
        strategies.put(PolicyProgram.BASIC, new BasicPremiumCalculationStrategy());
        strategies.put(PolicyProgram.STANDARD, new StandardPremiumCalculationStrategy());
        strategies.put(PolicyProgram.DELUXE, new DeluxePremiumCalculationStrategy());
    }

    public static PremiumCalculationStrategy getStrategy(PolicyProgram program) {
        PremiumCalculationStrategy strategy = strategies.get(program);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown policy program: " + program);
        }
        return strategy;
    }
}
