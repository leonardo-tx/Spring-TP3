package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Phone {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 32;

    private final String value;

    public static Phone valueOf(String value) {
        if (value == null) {
            throw new ValidationException("phone.null", "The phone number cannot be null.");
        }
        String cleanValue = value.strip();
        for (int i = 0; i < cleanValue.length(); i++) {
            char c = cleanValue.charAt(i);
            if (!Character.isDigit(c)) {
                throw new ValidationException("phone.invalid.character", "All characters of a phone must be numeric.");
            }
        }
        if (cleanValue.length() < MIN_LENGTH || cleanValue.length() > MAX_LENGTH) {
            String message = String.format("The phone number length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("phone.invalid.length", message);
        }
        return new Phone(cleanValue);
    }

    public static Phone fromInfra(String value) {
        return new Phone(value);
    }
}
