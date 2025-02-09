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
    void testCreateInvalidPremiumZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Premium(0.0, "EUR"));
    }

    @Test
    void testCreateInvalidPremiumNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> new Premium(-100.0, "EUR"));
    }

    @Test
    void testAddPremiumsSameCurrency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.add(premium2);
        assertEquals(150.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void testAddPremiumsDifferentCurrency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IllegalArgumentException.class, () -> premium1.add(premium2));
    }

    @Test
    void testSubtractPremiumsSameCurrency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "EUR");
        Premium result = premium1.subtract(premium2);
        assertEquals(50.0, result.getAmount());
        assertEquals("EUR", result.getCurrency());
    }

    @Test
    void testSubtractPremiumsDifferentCurrency() {
        Premium premium1 = new Premium(100.0, "EUR");
        Premium premium2 = new Premium(50.0, "USD");
        assertThrows(IllegalArgumentException.class, () -> premium1.subtract(premium2));
    }

    @Test
    void testToString() {
        Premium premium = new Premium(100.0, "EUR");
        assertEquals("100.0 EUR", premium.toString());
    }
}