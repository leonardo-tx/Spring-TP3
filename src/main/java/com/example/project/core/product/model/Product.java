package com.example.project.core.product.model;

import com.example.project.core.generic.model.Money;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class Product {
    private final Long id;
    private final Money price;

    public Product(Long id, Money price) {
        this.id = id;
        this.price = Objects.requireNonNull(price);
    }
}
