package de.sri.domain.valueobjects;

import java.util.Objects;

public final class CarDetails {
    private final double value;    
    private final String currency;
    private final int year;
    private final String licensePlate;

    public CarDetails(double value, String currency, int year, String licensePlate) {
        if (value <= 0) {
            throw new IllegalArgumentException("Car value must be positive");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Car currency cannot be empty");
        }
        if (year < 1900 || year > LocalDate.now().getYear() + 1) {
            throw new IllegalArgumentException("Car year must be valid");
        }
        if (!isValidLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("License plate must be valid");
        }

        this.value = value;
        this.currency = currency.trim();
        this.year = year;
        this.licensePlate = licensePlate.toUpperCase();
    }

    private boolean isValidLicensePlate(String licensePlate) {
        return licensePlate != null && licensePlate.matches("^[A-Z0-9-]+$");
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

    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDetails that = (CarDetails) o;
        return Double.compare(that.value, value) == 0 &&
                year == that.year &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(licensePlate, that.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency,year, licensePlate);
    }

    @Override
    public String toString() {
        return String.format("Car Value: %d %s \n Year: %d \n License Plate: %s", value, currency, year, licensePlate);
    }
}
