package com.example.project.api.request.category.mapper;

import com.example.project.api.request.category.dto.CategoryUpdateDTO;
import com.example.project.core.category.factory.CategoryFactory;
import com.example.project.core.category.factory.CategoryParams;
import com.example.project.core.category.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryUpdateMapperTest {
    @Mock
    private CategoryFactory categoryFactory;

    @InjectMocks
    private CategoryUpdateMapper categoryUpdateMapper;

    @Test
    void shouldMapSuccessfully() {
        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO(3L, "Electronics");

        when(categoryFactory.create(any(CategoryParams.class))).thenAnswer(invocation -> {
            CategoryParams params = invocation.getArgument(0);
            assertEquals(updateDTO.getId(), params.getId());
            assertEquals(updateDTO.getName(), params.getName());

            return mock(Category.class);
        });

        Category result = categoryUpdateMapper.toModel(updateDTO);

        assertNotNull(result);
        verify(categoryFactory, times(1)).create(any(CategoryParams.class));
    }
}
