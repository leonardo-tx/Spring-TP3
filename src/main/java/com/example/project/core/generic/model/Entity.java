package com.example.project.core.generic.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class Entity {
    private final EntityName name;
    private final EntityDocument document;
    private final Email email;
    private final Phone phone;

    public Entity(EntityName name, EntityDocument document, Email email, Phone phone) {
        this.name = Objects.requireNonNull(name);
        this.document = Objects.requireNonNull(document);
        this.email = Objects.requireNonNull(email);
        this.phone = Objects.requireNonNull(phone);
    }
}
