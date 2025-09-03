package com.example.project.core.product.model;

import com.example.project.core.generic.model.Money;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public final class Product {
    private final Long id;
    private final ProductName name;
    private final ProductDescription description;
    private final Money price;

    public Product(Long id, ProductName name, ProductDescription description, Money price) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.description = Objects.requireNonNull(description);
        this.price = Objects.requireNonNull(price);
    }
}
