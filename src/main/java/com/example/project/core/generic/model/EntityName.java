package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityName {
    public static final int MIN_LENGTH = 2;
    public static final int MAX_LENGTH = 128;

    private final String value;

    public static EntityName valueOf(String value) {
        if (value == null) {
            throw new ValidationException("entity.name.null", "The entity name cannot be null.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            String message = String.format("The entity name length must be between %d and %d characters.", MIN_LENGTH, MAX_LENGTH);
            throw new ValidationException("entity.name.invalid.length", message);
        }
        return new EntityName(value);
    }

    public static EntityName fromInfra(String value) {
        return new EntityName(value);
    }
}
