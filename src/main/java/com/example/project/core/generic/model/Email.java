package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Email {
    public static final int MIN_LENGTH = 5;
    public static final int MAX_LENGTH = 254;
    public static final Pattern PATTERN = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");

    private final String value;

    public static Email valueOf(String value) {
        if (value == null) {
            throw new ValidationException("email.null", "The email cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            String message = String.format("The email length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("email.invalid.length", message);
        }
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new ValidationException("email.invalid.format", "The email format is invalid.");
        }
        return new Email(value);
    }

    public static Email fromInfra(String email) {
        return new Email(email);
    }
}
