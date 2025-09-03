package com.example.project.core.product.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductDescription {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 512;

    private final String value;

    public static ProductDescription valueOf(String value) {
        if (value == null) {
            throw new ValidationException("product.description.null", "The product description cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            String message = String.format("The product description length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("product.description.invalid.length", message);
        }
        return new ProductDescription(value);
    }

    public static ProductDescription fromInfra(String value) {
        return new ProductDescription(value);
    }
}
