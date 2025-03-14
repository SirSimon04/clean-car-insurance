package de.sri.domain.exceptions;

public class PolicyNotFoundException extends BaseDomainException {
    public PolicyNotFoundException(int id) {
        super("The policy with id " + id + " was not found.");
    }
}
