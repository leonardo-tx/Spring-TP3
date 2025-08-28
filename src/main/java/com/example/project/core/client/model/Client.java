package com.example.project.core.client.model;

import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.Entity;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.RegisterDate;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Client extends Entity {
    private final Long id;
    private final RegisterDate registerDate;

    public Client(Long id, RegisterDate registerDate, EntityDocument document, Email email) {
        super(document, email);
        this.id = id;
        this.registerDate = Objects.requireNonNull(registerDate);
    }
}
