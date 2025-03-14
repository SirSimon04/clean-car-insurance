package de.sri.domain.exceptions;

public class PropertyNotNullException extends BaseDomainException {
    public PropertyNotNullException(String property) {
        super("Property " + property + " must not be null!");
    }
    
}
