package de.sri.domain.exceptions;

public class InvalidEmailAddress extends BaseDomainException {
    public InvalidEmailAddress(String email) {
        super("Invalid email address: " + email);
    }
    
}
