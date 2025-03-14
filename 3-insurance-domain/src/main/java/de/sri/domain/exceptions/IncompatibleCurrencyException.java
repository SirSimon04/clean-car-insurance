package de.sri.domain.exceptions;

public class IncompatibleCurrencyException extends BaseDomainException {
    public IncompatibleCurrencyException(String c1, String c2) {
        super("Incompatable currency: " + c1 + " and " + c2);
    }
}
