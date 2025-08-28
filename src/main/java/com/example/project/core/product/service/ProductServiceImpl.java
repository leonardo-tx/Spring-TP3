package com.example.project.core.product.service;

import com.example.project.core.product.model.Product;
import com.example.project.core.generic.exception.NotFoundException;
import com.example.project.core.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (product == null) {
            throw new IllegalArgumentException("The product must not be null.");
        }
        if (product.getId() == null) {
            throw new IllegalArgumentException("The product to delete must have an identifier.");
        }
        productRepository.delete(product);
    }

    @Override
    public Product create(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("The product must not be null.");
        }
        if (product.getId() != null) {
            throw new IllegalArgumentException("The product to create must not have an identifier.");
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Product partialProduct, Product targetProduct) {
        // TODO
        return null;
    }
}
