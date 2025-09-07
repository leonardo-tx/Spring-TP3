package com.example.project.infra.product.mapper;

import com.example.project.core.generic.model.Money;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.model.ProductDescription;
import com.example.project.core.product.model.ProductName;
import com.example.project.infra.product.persistence.ProductJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductJPAMapperTest {
    @InjectMocks
    private ProductJPAMapper productJPAMapper;

    @Test
    void shouldMapToModel() {
        ProductJPA entity = ProductJPA.builder()
                .id(1L)
                .name("Notebook Gamer")
                .description("Notebook gamer com 16GB RAM, RTX 4060")
                .price(new BigDecimal("4500.00"))
                .build();

        Product model = productJPAMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName().getValue());
        assertEquals(entity.getDescription(), model.getDescription().getValue());
        assertEquals(entity.getPrice(), model.getPrice().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Product model = new Product(
                1L,
                ProductName.fromInfra("Notebook Gamer"),
                ProductDescription.fromInfra("Notebook gamer com 16GB RAM, RTX 4060"),
                Money.fromInfra(new BigDecimal("4500.00"))
        );

        ProductJPA entity = productJPAMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName().getValue(), entity.getName());
        assertEquals(model.getDescription().getValue(), entity.getDescription());
        assertEquals(model.getPrice().getValue(), entity.getPrice());
    }
}
