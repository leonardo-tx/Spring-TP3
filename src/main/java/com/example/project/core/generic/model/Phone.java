package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Phone {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 32;
    public static final List<Pattern> PATTERNS = List.of(
        Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"),
        Pattern.compile("|^(\\\\+\\\\d{1,3}( )?)?(\\\\d{3} ?){2}\\\\d{3}$"),
        Pattern.compile("|^(\\+\\d{1,3}( )?)?(\\d{3} ?)(\\d{2} ?){2}\\d{2}$")
    );

    private final String value;

    public static Phone valueOf(String value) {
        if (value == null) {
            throw new ValidationException("phone.null", "The phone number cannot be null.");
        }
        for (Pattern pattern : PATTERNS) {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.matches()) {
                throw new ValidationException("phone.invalid.pattern", "The phone number has an invalid pattern.");
            }
        }
        String cleanValue = value.replaceAll("\\D", "");
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
