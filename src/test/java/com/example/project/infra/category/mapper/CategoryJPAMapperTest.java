package com.example.project.infra.category.mapper;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.model.CategoryName;
import com.example.project.infra.category.persistence.CategoryJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryJPAMapperTest {
    @InjectMocks
    private CategoryJPAMapper categoryJPAMapper;

    @Test
    void shouldMapToModel() {
        CategoryJPA entity = CategoryJPA.builder()
                .id(5L)
                .name("Categoria")
                .build();
        Category model = categoryJPAMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Category model = new Category(5L, CategoryName.fromInfra("Categoria"));
        CategoryJPA entity = categoryJPAMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName().getValue(), entity.getName());
    }
}
