package com.example.project.core.product.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static ProductName valueOf(String value) {
        if (value == null) {
            throw new ValidationException("product.name.null", "The product name cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            String message = String.format("The product name length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("product.name.invalid.length", message);
        }
        return new ProductName(value);
    }

    public static ProductName fromInfra(String value) {
        return new ProductName(value);
    }
}
