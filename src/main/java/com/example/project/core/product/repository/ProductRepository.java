package com.example.project.core.product.repository;

import com.example.project.core.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    boolean existsById(Long id);
    void delete(Product product);
    Product save(Product product);
}
