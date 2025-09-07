package com.example.project.api.request.product.mapper;

import com.example.project.api.request.product.dto.ProductCreateDTO;
import com.example.project.core.product.factory.ProductParams;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.factory.ProductFactory;
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
class ProductCreateMapperTest {
    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private ProductCreateMapper productCreateMapper;

    @Test
    void shouldMapSuccessfully() {
        ProductCreateDTO createDTO = new ProductCreateDTO("Produto", "Descrição do Produto", new BigDecimal("52.99"));

        when(productFactory.create(any(ProductParams.class))).thenAnswer(invocation -> {
            ProductParams params = invocation.getArgument(0);
            assertNull(params.getId());
            assertEquals(createDTO.getName(), params.getName());
            assertEquals(createDTO.getDescription(), params.getDescription());
            assertEquals(createDTO.getPrice(), params.getPrice());

            return mock(Product.class);
        });

        Product result = productCreateMapper.toModel(createDTO);

        assertNotNull(result);
        verify(productFactory, times(1)).create(any(ProductParams.class));
    }
}
