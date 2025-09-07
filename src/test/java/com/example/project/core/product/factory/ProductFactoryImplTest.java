package com.example.project.core.product.factory;

import com.example.project.core.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductFactoryImplTest {
    @InjectMocks
    private ProductFactoryImpl productFactory;

    @Test
    void shouldCreate() {
        ProductParams params = new ProductParams(
                4L,
                "Produto",
                "",
                new BigDecimal("99.99")
        );
        Product product = productFactory.create(params);

        assertEquals(params.getId(), product.getId());
        assertEquals(params.getName(), product.getName().getValue());
        assertEquals(params.getDescription(), product.getDescription().getValue());
        assertEquals(params.getPrice(), product.getPrice().getValue());
    }
}
