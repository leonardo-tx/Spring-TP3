package com.example.project.infra.product.mapper;

import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.product.model.Product;
import com.example.project.infra.product.persistence.ProductJPA;
import org.springframework.stereotype.Component;

@Component
public class ProductJPAMapper implements Mapper<Product, ProductJPA> {
    @Override
    public Product toModel(ProductJPA entity) {
        return null;
    }

    @Override
    public ProductJPA toEntity(Product model) {
        return null;
    }
}
