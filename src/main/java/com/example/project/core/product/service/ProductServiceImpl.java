package com.example.project.core.product.service;

import com.example.project.core.product.model.Product;
import com.example.project.core.generic.exception.NotFoundException;
import com.example.project.core.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new NotFoundException("product.not.found", "Could not find the product with the specified id.")
        );
    }

    @Override
    public void delete(Product product) {
        Objects.requireNonNull(product);
        if (product.getId() == null) {
            throw new IllegalArgumentException("The product to delete must have an identifier.");
        }
        productRepository.delete(product);
    }

    @Override
    public Product create(Product product) {
        Objects.requireNonNull(product);
        if (product.getId() != null) {
            throw new IllegalArgumentException("The product to create must not have an identifier.");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Objects.requireNonNull(product);
        if (product.getId() == null) {
            throw new IllegalArgumentException("The product to update must have an identifier.");
        }
        return productRepository.save(product);
    }
}
