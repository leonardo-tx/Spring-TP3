package com.example.project.api.response.product.mapper;

import com.example.project.api.response.product.dto.ProductViewDTO;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductViewMapper implements OutputMapper<Product, ProductViewDTO> {
    @Override
    public ProductViewDTO toEntity(Product model) {
        return null;
    }
}
