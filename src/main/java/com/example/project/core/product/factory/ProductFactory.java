package com.example.project.core.product.factory;

import com.example.project.core.product.model.Product;

public interface ProductFactory {
    Product create(ProductParams params);
}
