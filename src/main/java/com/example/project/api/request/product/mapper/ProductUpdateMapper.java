package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductUpdateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateMapper implements InputMapper<Product, ProductUpdateDTO> {
    @Override
    public Product toModel(ProductUpdateDTO entity) {
        return null;
    }
}
