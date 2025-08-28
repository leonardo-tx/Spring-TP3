package com.example.project.core.generic.model;

import com.example.project.core.generic.exception.ValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegisterDate {
    private final LocalDate value;

    public static RegisterDate valueOf(LocalDate value) {
        if (value == null) {
            throw new ValidationException("register.date.null", "The register date cannot be null.");
        }
        LocalDate now = LocalDate.now();
        if (value.isAfter(now)) {
            throw new ValidationException("register.date.invalid.range", "The register date can't be after today.");
        }
        return new RegisterDate(value);
    }

    public static RegisterDate fromInfra(LocalDate value) {
        return new RegisterDate(value);
    }
}
