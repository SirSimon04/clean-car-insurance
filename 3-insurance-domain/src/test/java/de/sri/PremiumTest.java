package de.sri;

import de.sri.domain.exceptions.IncompatibleCurrencyException;
import de.sri.domain.exceptions.InvalidPremiumAmountException;
import de.sri.domain.exceptions.PropertyNotNullException;
import de.sri.domain.valueobjects.Premium;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PremiumTest {

    @Test
    void create_valid_premium_object() throws InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium = new Premium(100.0, "EUR");
        assertEquals(100.0, premium.getAmount());
        assertEquals("EUR", premium.getCurrency());
    }

    @Test
    void create_invalid_premium_object_zero_amount() {
        assertThrows(InvalidPremiumAmountException.class, () -> new Premium(0.0, "EUR"));
    }

    @Test
    void create_invalid_premium_object_negative_amount() {
        assertThrows(InvalidPremiumAmountException.class, () -> new Premium(-100.0, "EUR"));
    }

    @Test
    void create_invalid_premium_object_null_currency() {
        assertThrows(PropertyNotNullException.class, () -> new Premium(100.0, null));
    }

    @Test
    void add_premiums_with_same_currency()
            throws IncompatibleCurrencyException, InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.add(premium2);
        assertEquals(150.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void add_premiums_with_different_currencies() throws InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IncompatibleCurrencyException.class, () -> premium1.add(premium2));
    }

    @Test
    void subtract_premiums_with_same_currency()
            throws IncompatibleCurrencyException, InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.subtract(premium2);
        assertEquals(50.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void subtract_premiums_with_different_currencies() throws InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IncompatibleCurrencyException.class, () -> premium1.subtract(premium2));
    }

    @Test
    void to_string_method() throws InvalidPremiumAmountException, PropertyNotNullException {
        Premium premium = new Premium(100.0, "EUR");
        assertEquals("100.0 EUR", premium.toString());
    }
}
