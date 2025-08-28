package com.example.project.core.supplier.model;

import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.Entity;
import com.example.project.core.generic.model.EntityDocument;
import lombok.Getter;

@Getter
public final class Supplier extends Entity {
    private final Long id;

    public Supplier(Long id, EntityDocument document, Email email) {
        super(document, email);
        this.id = id;
    }
}
