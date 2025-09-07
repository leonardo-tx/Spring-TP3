package com.example.project.api.response.product.mapper;

import com.example.project.api.response.product.dto.ProductViewDTO;
import com.example.project.core.generic.model.Money;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.model.ProductDescription;
import com.example.project.core.product.model.ProductName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ProductViewMapperTest {
    @InjectMocks
    private ProductViewMapper productViewMapper;

    @Test
    void shouldParseSuccessfully() {
        Product product = new Product(
                9L,
                ProductName.fromInfra("Laptop"),
                ProductDescription.fromInfra("aaaaaa"),
                Money.fromInfra(new BigDecimal("2500.00"))
        );

        ProductViewDTO result = productViewMapper.toEntity(product);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName().getValue(), result.getName());
        assertEquals(product.getDescription().getValue(), result.getDescription());
        assertEquals(product.getPrice().getValue(), result.getPrice());
    }
}
