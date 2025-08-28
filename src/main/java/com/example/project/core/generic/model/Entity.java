package com.example.project.core.generic.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class Entity {
    private final EntityDocument document;
    private final Email email;

    public Entity(EntityDocument document, Email email) {
        this.document = Objects.requireNonNull(document);
        this.email = Objects.requireNonNull(email);
    }
}
