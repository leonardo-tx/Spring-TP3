package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void shouldCreateEmailWithValidValue() {
        String validEmail = "test@example.com";

        Email email = Email.valueOf(validEmail);

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> Email.valueOf(null));
        assertEquals("email.null", exception.getCode());
        assertEquals("The email cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "abc", "abcd"})
    void shouldThrowExceptionWhenEmailIsTooShort(String shortEmail) {
        ValidationException exception = assertThrows(ValidationException.class, () -> Email.valueOf(shortEmail));

        assertEquals("email.invalid.length", exception.getCode());
        assertTrue(exception.getMessage().contains("The email length must be between"));
    }

    @Test
    void shouldThrowExceptionWhenEmailIsTooLong() {
        String longEmail = "a".repeat(Email.MAX_LENGTH + 1) + "@example.com";

        ValidationException exception = assertThrows(ValidationException.class, () -> Email.valueOf(longEmail));

        assertEquals("email.invalid.length", exception.getCode());
        assertTrue(exception.getMessage().contains("The email length must be between"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "test@",
            "@example.com",
            "test@.com",
            "test@example.",
            "test@example..com",
            "test@.example.com",
            "test@-example.com"
    })
    void shouldThrowExceptionWhenEmailHasInvalidFormat(String invalidEmail) {
        ValidationException exception = assertThrows(ValidationException.class, () -> Email.valueOf(invalidEmail));
        assertEquals("email.invalid.format", exception.getCode());
        assertEquals("The email format is invalid.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.br",
            "user_name@domain.gov.br",
            "user-name@domain.edu",
            "user123@domain.gg",
            "user+tag@domain.kr",
            "user.name@sub.domain.com",
            "first.last@example.co.uk",
            "email@domain-one.xyz",
            "email@domain.name",
            "email@domain.co.jp",
            "1234567890@domain.com",
            "email@domain-web.com"
    })
    void shouldAcceptValidEmailFormats(String validEmail) {
        assertDoesNotThrow(() -> {
            Email email = Email.valueOf(validEmail);
            assertNotNull(email);
            assertEquals(validEmail, email.getValue());
        });
    }

    @Test
    void shouldCreateEmailUsingFromInfraMethod() {
        String emailValue = "test@infra.com";
        Email email = Email.fromInfra(emailValue);

        assertNotNull(email);
        assertEquals(emailValue, email.getValue());
    }

    @Test
    void fromInfraShouldBypassValidation() {
        String invalidEmail = "invalid-email";
        Email email = Email.fromInfra(invalidEmail);

        assertNotNull(email);
        assertEquals(invalidEmail, email.getValue());
    }

    @Test
    void shouldRespectLengthConstants() {
        String minLengthEmail = "a@b.cd";
        assertDoesNotThrow(() -> Email.valueOf(minLengthEmail));

        String domainPart = "@abc.defgh";
        String localPart = "a".repeat(Email.MAX_LENGTH - domainPart.length());
        String maxLengthEmail = localPart + domainPart;
        assertEquals(Email.MAX_LENGTH, maxLengthEmail.length());
        assertDoesNotThrow(() -> Email.valueOf(maxLengthEmail));
    }
}
