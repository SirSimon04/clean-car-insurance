package de.sri.domain.exceptions;

public class CustomerNotFoundException extends BaseDomainException {
    public CustomerNotFoundException(int id) {
        super("The user with id " + id + " was not found.");
    }
}
