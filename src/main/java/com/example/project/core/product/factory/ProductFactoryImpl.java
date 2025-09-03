package com.example.project.core.product.factory;

import com.example.project.core.generic.model.Money;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.model.ProductDescription;
import com.example.project.core.product.model.ProductName;
import org.springframework.stereotype.Component;

@Component
public class ProductFactoryImpl implements ProductFactory {
    @Override
    public Product create(ProductParams params) {
        ProductName name = ProductName.valueOf(params.getName());
        ProductDescription description = ProductDescription.valueOf(params.getDescription());
        Money price = Money.valueOf(params.getPrice());

        return new Product(params.getId(), name, description, price);
    }
}
