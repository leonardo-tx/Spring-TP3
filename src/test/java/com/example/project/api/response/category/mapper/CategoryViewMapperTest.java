package com.example.project.api.response.category.mapper;

import com.example.project.api.response.category.dto.CategoryViewDTO;
import com.example.project.core.category.model.Category;
import com.example.project.core.category.model.CategoryName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CategoryViewMapperTest {
    @InjectMocks
    private CategoryViewMapper categoryViewMapper;

    @Test
    void shouldParseSuccessfully() {
        Category category = new Category(2L, CategoryName.fromInfra("Nome da Categoria"));
        CategoryViewDTO result = categoryViewMapper.toEntity(category);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName().getValue(), result.getName());
    }
}
