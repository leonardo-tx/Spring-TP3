package com.example.project.api.request.category.mapper;

import com.example.project.api.request.category.dto.CategoryCreateDTO;
import com.example.project.core.category.factory.CategoryFactory;
import com.example.project.core.category.factory.CategoryParams;
import com.example.project.core.category.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryCreateMapperTest {
    @Mock
    private CategoryFactory categoryFactory;

    @InjectMocks
    private CategoryCreateMapper categoryCreateMapper;

    @Test
    void shouldMapSuccessfully() {
        CategoryCreateDTO createDTO = new CategoryCreateDTO("Electronics");

        when(categoryFactory.create(any(CategoryParams.class))).thenAnswer(invocation -> {
            CategoryParams params = invocation.getArgument(0);
            assertNull(params.getId());
            assertEquals(createDTO.getName(), params.getName());

            return mock(Category.class);
        });

        Category result = categoryCreateMapper.toModel(createDTO);

        assertNotNull(result);
        verify(categoryFactory, times(1)).create(any(CategoryParams.class));
    }
}
