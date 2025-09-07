package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    @Test
    void shouldCreatePhoneWithValidNumericValue() {
        String validPhone = "11987654321";

        Phone phone = Phone.valueOf(validPhone);

        assertNotNull(phone);
        assertEquals(validPhone, phone.getValue());
    }

    @Test
    void shouldStripWhitespaceFromPhoneNumber() {
        String phoneWithWhitespace = "  11987654321  ";
        String expectedPhone = "11987654321";

        Phone phone = Phone.valueOf(phoneWithWhitespace);

        assertNotNull(phone);
        assertEquals(expectedPhone, phone.getValue());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Phone.valueOf(null));

        assertEquals("phone.null", exception.getCode());
        assertEquals("The phone number cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a*", "abc", "1198765abc"})
    void shouldThrowExceptionWhenPhoneContainsNonNumericCharacters(String invalidPhone) {
        ValidationException exception = assertThrows(ValidationException.class, () -> Phone.valueOf(invalidPhone));

        assertEquals("phone.invalid.character", exception.getCode());
        assertEquals("All characters of a phone must be numeric.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsTooShort() {
        String shortPhone = "1";

        ValidationException exception = assertThrows(ValidationException.class, () -> Phone.valueOf(shortPhone));

        assertEquals("phone.invalid.length", exception.getCode());
        assertTrue(exception.getMessage().contains("The phone number length must be between"));
        assertTrue(exception.getMessage().contains(String.valueOf(Phone.MIN_LENGTH)));
        assertTrue(exception.getMessage().contains(String.valueOf(Phone.MAX_LENGTH)));
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsTooLong() {
        String longPhone = "1".repeat(Phone.MAX_LENGTH + 1);

        ValidationException exception = assertThrows(ValidationException.class, () -> Phone.valueOf(longPhone));

        assertEquals("phone.invalid.length", exception.getCode());
        assertTrue(exception.getMessage().contains("The phone number length must be between"));
        assertTrue(exception.getMessage().contains(String.valueOf(Phone.MIN_LENGTH)));
        assertTrue(exception.getMessage().contains(String.valueOf(Phone.MAX_LENGTH)));
    }

    @Test
    void shouldAcceptPhoneWithMinimumLength() {
        String minLengthPhone = "12";

        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(minLengthPhone);
            assertNotNull(phone);
            assertEquals(minLengthPhone, phone.getValue());
        });
    }

    @Test
    void shouldAcceptPhoneWithMaximumLength() {
        String maxLengthPhone = "1".repeat(Phone.MAX_LENGTH);
        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(maxLengthPhone);
            assertNotNull(phone);
            assertEquals(maxLengthPhone, phone.getValue());
        });
    }

    @Test
    void shouldCreatePhoneUsingFromInfraMethod() {
        String phoneValue = "11987654321";

        Phone phone = Phone.fromInfra(phoneValue);

        assertNotNull(phone);
        assertEquals(phoneValue, phone.getValue());
    }

    @Test
    void fromInfraShouldBypassValidation() {
        String invalidPhone = "11abc123";

        Phone phone = Phone.fromInfra(invalidPhone);

        assertNotNull(phone);
        assertEquals(invalidPhone, phone.getValue());
    }

    @Test
    void fromInfraShouldNotStripWhitespace() {
        String phoneWithWhitespace = "  11987654321  ";

        Phone phone = Phone.fromInfra(phoneWithWhitespace);

        assertNotNull(phone);
        assertEquals(phoneWithWhitespace, phone.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "11",
            "123",
            "11987654321",
            "08001234567",
            "99999999999999999999999999999999"
    })
    void shouldAcceptVariousValidPhoneNumbers(String validPhone) {
        assertDoesNotThrow(() -> {
            Phone phone = Phone.valueOf(validPhone);
            assertNotNull(phone);
            assertEquals(validPhone, phone.getValue());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " 11987654321 ",
            "  11  ",
            "  1234567890  "
    })
    void shouldStripWhitespaceFromVariousPhoneNumbers(String phoneWithWhitespace) {
        String expectedPhone = phoneWithWhitespace.strip();

        Phone phone = Phone.valueOf(phoneWithWhitespace);

        assertNotNull(phone);
        assertEquals(expectedPhone, phone.getValue());
    }
}
