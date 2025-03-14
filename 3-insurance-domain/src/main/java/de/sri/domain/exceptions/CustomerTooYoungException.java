package de.sri.domain.exceptions;

public class CustomerTooYoungException extends BaseDomainException {
    public CustomerTooYoungException() {
        super("Customer has to be 18 years old to create a policy!");
    }
}
