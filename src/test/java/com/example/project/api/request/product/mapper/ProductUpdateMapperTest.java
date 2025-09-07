package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductUpdateDTO;
import com.example.project.core.product.factory.ProductFactory;
import com.example.project.core.product.factory.ProductParams;
import com.example.project.core.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductUpdateMapperTest {
    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private ProductUpdateMapper productUpdateMapper;

    @Test
    void shouldMapSuccessfully() {
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(6L, "Produto", "Descrição do Produto", new BigDecimal("52.99"));

        when(productFactory.create(any(ProductParams.class))).thenAnswer(invocation -> {
            ProductParams params = invocation.getArgument(0);
            assertEquals(updateDTO.getId(), params.getId());
            assertEquals(updateDTO.getName(), params.getName());
            assertEquals(updateDTO.getDescription(), params.getDescription());
            assertEquals(updateDTO.getPrice(), params.getPrice());

            return mock(Product.class);
        });

        Product result = productUpdateMapper.toModel(updateDTO);

        assertNotNull(result);
        verify(productFactory, times(1)).create(any(ProductParams.class));
    }
}
