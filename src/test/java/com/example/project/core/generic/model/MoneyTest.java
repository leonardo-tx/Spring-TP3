package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {
    @ParameterizedTest
    @ValueSource(strings = {"2.55", "0.00", "4320.99", "999999999999.99", "555.55"})
    void shouldCreateMoneyWithValidValue(BigDecimal value) {
        Money money = Money.valueOf(value);
        assertEquals(value, money.getValue());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Money.valueOf(null));

        assertEquals("money.null", exception.getCode());
        assertEquals("The money cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"223.4", "563.456", "1243", "6.0"})
    void shouldThrowExceptionWhenScaleIsNotTwo(String invalidValue) {
        BigDecimal value = new BigDecimal(invalidValue);

        ValidationException exception = assertThrows(ValidationException.class, () -> Money.valueOf(value));

        assertEquals("money.invalid.scale", exception.getCode());
        assertEquals("The money value must have exactly 2 decimal places.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPrecisionExceedsMax() {
        BigDecimal largeValue = new BigDecimal("52746628989234.67");

        ValidationException exception = assertThrows(ValidationException.class, () -> Money.valueOf(largeValue));

        String expectedMessage = String.format("The money value cannot have precision above %d", Money.MAX_PRECISION);
        assertEquals("money.invalid.precision", exception.getCode());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1.50", "-0.01", "-125.99"})
    void shouldThrowExceptionWhenValueIsNegative(String negativeValue) {
        BigDecimal value = new BigDecimal(negativeValue);

        ValidationException exception = assertThrows(ValidationException.class, () -> Money.valueOf(value));

        assertEquals("money.negative", exception.getCode());
        assertEquals("The money value cannot be negative.", exception.getMessage());
    }
}
