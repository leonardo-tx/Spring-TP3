package com.example.project.core.category.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class CategoryName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 32;

    private final String value;

    public static CategoryName valueOf(String value) {
        if (value == null) {
            throw new ValidationException("category.name.null", "The category name cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            String message = String.format("The category name length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("category.name.invalid.length", message);
        }
        return new CategoryName(value);
    }

    public static CategoryName fromInfra(String value) {
        return new CategoryName(value);
    }
}
