package com.example.project.core.client.model;

import com.example.project.core.generic.exception.ValidationException;
import com.example.project.core.generic.model.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public final class Client extends Entity {
    private final Long id;
    private final LocalDate registerDate;

    public Client(Long id, LocalDate registerDate, EntityName name, EntityDocument document, Email email, Phone phone) {
        super(name, document, email, phone);
        if (registerDate == null) {
            throw new ValidationException("client.register.date.null", "The client register date cannot be null.");
        }
        this.id = id;
        this.registerDate = Objects.requireNonNull(registerDate);
    }
}
