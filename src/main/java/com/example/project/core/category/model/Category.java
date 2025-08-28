package com.example.project.core.category.model;

import lombok.Getter;

@Getter
public final class Category {
    private final Long id;

    public Category(Long id) {
        this.id = id;
    }
}
