package com.example.project.core.category.factory;

import com.example.project.core.category.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryFactoryImplTest {
    @InjectMocks
    private CategoryFactoryImpl categoryFactory;

    @Test
    void shouldCreate() {
        CategoryParams params = new CategoryParams(10L, "Teste");
        Category category = categoryFactory.create(params);

        assertEquals(params.getId(), category.getId());
        assertEquals(params.getName(), category.getName().getValue());
    }
}
