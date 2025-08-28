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
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private final String value;

    public static Email valueOf(String value) {
        if (value == null) {
            throw new ValidationException("email.null", "The email cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new ValidationException("email.invalid.length", "The email length must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters.");
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
