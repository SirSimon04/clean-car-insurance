package de.sri.domain.exceptions;

public class InvalidPremiumAmountException extends BaseDomainException {
    public InvalidPremiumAmountException() {
        super("Premium Amount must be positive!");
    }

}
