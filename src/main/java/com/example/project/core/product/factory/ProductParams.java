package com.example.project.core.product.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public final class ProductParams {
    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
}
