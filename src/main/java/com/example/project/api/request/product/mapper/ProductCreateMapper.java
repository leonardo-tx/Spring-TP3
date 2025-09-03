package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductCreateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.product.factory.ProductFactory;
import com.example.project.core.product.factory.ProductParams;
import com.example.project.core.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreateMapper implements InputMapper<Product, ProductCreateDTO> {
    private final ProductFactory productFactory;

    @Override
    public Product toModel(ProductCreateDTO entity) {
        ProductParams params = new ProductParams(
                null,
                entity.getName(),
                entity.getDescription(),
                entity.getPrice()
        );
        return productFactory.create(params);
    }
}
