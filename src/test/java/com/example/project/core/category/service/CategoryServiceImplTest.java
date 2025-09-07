package com.example.project.core.category.service;

import com.example.project.core.category.model.Category;
import com.example.project.core.category.repository.CategoryRepository;
import com.example.project.core.generic.exception.CoreException;
import com.example.project.core.generic.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void shouldReturnOnGetAll() {
        List<Category> categories = List.of(mock(Category.class), mock(Category.class));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> returnedCategories = categoryService.getAll();

        assertEquals(2, returnedCategories.size());
        assertSame(categories, returnedCategories);

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOnGetById() {
        Category category = mock(Category.class);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        assertSame(category, categoryService.getById(1L));

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenNotFoundOnGetById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CoreException exception = assertThrows(NotFoundException.class, () -> categoryService.getById(1L));

        assertEquals("category.not.found", exception.getCode());
        assertEquals("Could not find the category with the specified id.", exception.getMessage());

        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteCategory() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(2L);

        assertDoesNotThrow(() -> categoryService.delete(category));

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void shouldNotDeleteCategoryWithoutId() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.delete(category)
        );

        assertEquals("The category to delete must have an identifier.", exception.getMessage());
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void shouldCreateCategory() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(null);
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = assertDoesNotThrow(() -> categoryService.create(category));

        assertSame(category, createdCategory);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldNotCreateCategoryWithId() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(3L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.create(category)
        );

        assertEquals("The category to create must not have an identifier.", exception.getMessage());
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void shouldUpdateCategory() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(4L);
        when(categoryRepository.existsById(4L)).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(category);

        Category updatedCategory = assertDoesNotThrow(() -> categoryService.update(category));

        assertSame(category, updatedCategory);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldNotUpdateCategoryWithoutId() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> categoryService.update(category)
        );

        assertEquals("The category to update must have an identifier.", exception.getMessage());
        verifyNoInteractions(categoryRepository);
    }

    @Test
    void shouldNotUpdateIfNotFound() {
        Category category = mock(Category.class);
        when(category.getId()).thenReturn(4L);
        when(categoryRepository.existsById(4L)).thenReturn(false);

        CoreException exception = assertThrows(NotFoundException.class, () -> categoryService.update(category));

        assertEquals("category.not.found", exception.getCode());
        assertEquals("Could not find the category with the specified id.", exception.getMessage());

        verifyNoMoreInteractions(categoryRepository);
    }
}
