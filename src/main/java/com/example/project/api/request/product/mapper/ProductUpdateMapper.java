package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductUpdateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.product.factory.ProductFactory;
import com.example.project.core.product.factory.ProductParams;
import com.example.project.core.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUpdateMapper implements InputMapper<Product, ProductUpdateDTO> {
    private final ProductFactory productFactory;

    @Override
    public Product toModel(ProductUpdateDTO entity) {
        ProductParams params = new ProductParams(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice()
        );
        return productFactory.create(params);
    }
}
