package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductCreateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateMapper implements InputMapper<Product, ProductCreateDTO> {
    @Override
    public Product toModel(ProductCreateDTO entity) {
        return null;
    }
}
