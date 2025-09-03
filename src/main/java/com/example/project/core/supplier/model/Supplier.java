package com.example.project.core.supplier.model;

import com.example.project.core.generic.model.*;
import lombok.Getter;

@Getter
public final class Supplier extends Entity {
    private final Long id;

    public Supplier(Long id, EntityName name, EntityDocument document, Email email, Phone phone) {
        super(name, document, email, phone);
        this.id = id;
    }
}
