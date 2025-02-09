package de.sri;

import de.sri.domain.valueobjects.Premium;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PremiumTest {

    @Test
    void create_valid_premium_object() {
        Premium premium = new Premium(100.0, "EUR");
        assertEquals(100.0, premium.getAmount());
        assertEquals("EUR", premium.getCurrency());
    }

    @Test
    void create_invalid_premium_object_zero_amount() {
        assertThrows(IllegalArgumentException.class, () -> new Premium(0.0, "EUR"));
    }

    @Test
    void create_invalid_premium_object_negative_amount() {
        assertThrows(IllegalArgumentException.class, () -> new Premium(-100.0, "EUR"));
    }

    @Test
    void add_premiums_with_same_currency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.add(premium2);
        assertEquals(150.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void add_premiums_with_different_currencies() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IllegalArgumentException.class, () -> premium1.add(premium2));
    }

    @Test
    void subtract_premiums_with_same_currency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.subtract(premium2);
        assertEquals(50.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void subtract_premiums_with_different_currencies() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IllegalArgumentException.class, () -> premium1.subtract(premium2));
    }

    @Test
    void to_string_method() {
        Premium premium = new Premium(100.0, "EUR");
        assertEquals("100.0 EUR", premium.toString());
    }
}