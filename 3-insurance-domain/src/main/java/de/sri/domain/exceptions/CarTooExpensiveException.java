package de.sri.domain.exceptions;

public class CarTooExpensiveException extends BaseDomainException {
	public CarTooExpensiveException(int value) {
		super("Car value cannot be more than " + value + "!");
	}
}
