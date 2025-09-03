package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Money {
    public static final int SCALE = 2;
    public static final int MAX_PRECISION = 15;

    private final BigDecimal value;

    public static Money valueOf(BigDecimal value) {
        if (value == null) {
            throw new ValidationException("money.null", "The money cannot be null.");
        }
        if (value.scale() != SCALE) {
            throw new ValidationException("money.invalid.scale", "The money value must have exactly 2 decimal places.");
        }
        if (value.precision() > MAX_PRECISION) {
            String message = String.format("The money value cannot have precision above %d", MAX_PRECISION);
            throw new ValidationException("money.invalid.precision", message);
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("money.negative", "The money value cannot be negative.");
        }
        return new Money(value);
    }

    public static Money fromInfra(BigDecimal value) {
        return new Money(value);
    }
}
