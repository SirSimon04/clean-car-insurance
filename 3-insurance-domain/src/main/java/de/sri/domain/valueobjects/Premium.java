package de.sri.domain.valueobjects;

import de.sri.domain.exceptions.IncompatibleCurrencyException;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;

public class Premium {
    private final double amount;
    private final String currency;

    public Premium(double amount, String currency) throws InvalidPremiumAmountException, PropertyNotNullException {
        if (amount <= 0) {
            throw new InvalidPremiumAmountException();
        }
        this.amount = amount;
        if (currency == null) {
            throw new PropertyNotNullException("currency");
        }
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Premium add(Premium other)
            throws IncompatibleCurrencyException, InvalidPremiumAmountException, PropertyNotNullException {
        if (!this.currency.equals(other.currency)) {
            throw new IncompatibleCurrencyException(this.getCurrency(), other.getCurrency());
        }
        return new Premium(this.amount + other.amount, this.currency);
    }

    public Premium subtract(Premium other)
            throws IncompatibleCurrencyException, InvalidPremiumAmountException, PropertyNotNullException {
        if (!this.currency.equals(other.currency)) {
            throw new IncompatibleCurrencyException(this.getCurrency(), other.getCurrency());
        }
        return new Premium(this.amount - other.amount, this.currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
