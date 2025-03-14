package de.sri.domain.valueobjects;

import java.util.Objects;

import de.sri.domain.exceptions.InvalidEmailAddress;
import de.sri.domain.exceptions.PropertyNotNullException;

public final class EmailAddress {
    private final String address;

    public EmailAddress(String address) throws InvalidEmailAddress, PropertyNotNullException {
        if (address == null) {
            throw new PropertyNotNullException("EmailAddress.address");
        }
        if (!isValid(address)) {
            throw new InvalidEmailAddress(address);
        }
        this.address = address.toLowerCase();
    }

    public String getEmailAddress() {
        return address;
    }

    public String getDomain() {
        return address.substring(address.indexOf("@") + 1);
    }

    private boolean isValid(String address) {
        return address != null && address.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return address;
    }
}
