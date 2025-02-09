package de.sri.domain.valueobjects;

import java.time.LocalDate;
import java.util.Objects;

public final class CarValue {
    private final double value;    
    private final String currency;
    private final int year;    

    public CarValue(double value, String currency, int year) {
        if (value <= 0) {
            throw new IllegalArgumentException("Car value must be positive");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Car currency cannot be empty");
        }
        if (year < 1900 || year > LocalDate.now().getYear() + 1) {
            throw new IllegalArgumentException("Car year must be valid");
        }        

        this.value = value;
        this.currency = currency.trim();
        this.year = year;        
    }

    public double getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarValue that = (CarValue) o;
        return Double.compare(that.value, value) == 0 &&
                year == that.year &&
                Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency,year);
    }

    @Override
    public String toString() {
        return String.format("Car Value: %d %s \n Year: %d", value, currency, year);
    }
}
