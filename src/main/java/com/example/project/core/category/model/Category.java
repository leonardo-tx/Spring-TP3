package com.example.project.core.category.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public final class Category {
    private final Long id;
    private final CategoryName name;

    public Category(Long id, CategoryName name) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }
}
