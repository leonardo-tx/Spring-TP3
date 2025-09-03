package com.example.project.infra.product.mapper;

import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.generic.model.Money;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.model.ProductDescription;
import com.example.project.core.product.model.ProductName;
import com.example.project.infra.product.persistence.ProductJPA;
import org.springframework.stereotype.Component;

@Component
public class ProductJPAMapper implements Mapper<Product, ProductJPA> {
    @Override
    public Product toModel(ProductJPA entity) {
        return new Product(
                entity.getId(),
                ProductName.fromInfra(entity.getName()),
                ProductDescription.fromInfra(entity.getDescription()),
                Money.fromInfra(entity.getPrice())
        );
    }

    @Override
    public ProductJPA toEntity(Product model) {
        return ProductJPA.builder()
                .id(model.getId())
                .name(model.getName().getValue())
                .description(model.getDescription().getValue())
                .price(model.getPrice().getValue())
                .build();
    }
}

