package com.example.project.core.product.service;

import com.example.project.core.product.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    void delete(Product product);
    Product create(Product product);
    Product update(Product partialProduct, Product targetProduct);
}
