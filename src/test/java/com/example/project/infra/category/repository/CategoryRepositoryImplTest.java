package com.example.project.infra.category.repository;

import com.example.project.core.category.model.Category;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.category.persistence.CategoryJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {
    @Mock
    private CategoryRepositoryJPA categoryRepositoryJPA;

    @Mock
    private Mapper<Category, CategoryJPA> categoryJPAMapper;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    @Test
    void shouldFindAll() {
        List<CategoryJPA> entities = List.of(mock(CategoryJPA.class), mock(CategoryJPA.class));
        when(categoryRepositoryJPA.findAll()).thenReturn(entities);

        List<Category> categories = assertDoesNotThrow(() -> categoryRepository.findAll());

        assertEquals(entities.size(), categories.size());
        verify(categoryJPAMapper, times(entities.size())).toModel(any(CategoryJPA.class));
    }

    @Test
    void shouldFindById() {
        CategoryJPA mockCategoryJPA = mock(CategoryJPA.class);
        Category mockCategory = mock(Category.class);

        when(categoryRepositoryJPA.findById(3L)).thenReturn(Optional.of(mockCategoryJPA));
        when(categoryJPAMapper.toModel(mockCategoryJPA)).thenReturn(mockCategory);

        Optional<Category> optionalCategory = assertDoesNotThrow(() -> categoryRepository.findById(3L));

        assertTrue(optionalCategory.isPresent());
        assertSame(mockCategory, optionalCategory.get());

        verify(categoryJPAMapper, times(1)).toModel(mockCategoryJPA);
    }

    @Test
    void shouldReturnOptionEmptyWhenNotFindById() {
        when(categoryRepositoryJPA.findById(3L)).thenReturn(Optional.empty());

        Optional<Category> optionalCategory = assertDoesNotThrow(() -> categoryRepository.findById(3L));

        assertTrue(optionalCategory.isEmpty());

        verifyNoInteractions(categoryJPAMapper);
    }

    @Test
    void shouldDelete() {
        Category mockCategory = mock(Category.class);
        when(mockCategory.getId()).thenReturn(4L);

        assertDoesNotThrow(() -> categoryRepository.delete(mockCategory));

        verify(categoryRepositoryJPA).deleteById(4L);
    }

    @Test
    void shouldSave() {
        Category mockCategory = mock(Category.class);
        Category mockSavedCategory = mock(Category.class);
        CategoryJPA mockCategoryJPA = mock(CategoryJPA.class);
        CategoryJPA mockSavedCategoryJPA = mock(CategoryJPA.class);

        when(categoryJPAMapper.toEntity(mockCategory)).thenReturn(mockCategoryJPA);
        when(categoryRepositoryJPA.save(mockCategoryJPA)).thenReturn(mockSavedCategoryJPA);
        when(categoryJPAMapper.toModel(mockSavedCategoryJPA)).thenReturn(mockSavedCategory);

        Category returnedCategory = assertDoesNotThrow(() -> categoryRepository.save(mockCategory));

        assertSame(mockSavedCategory, returnedCategory);
        verify(categoryJPAMapper, times(1)).toEntity(mockCategory);
        verify(categoryJPAMapper, times(1)).toModel(mockSavedCategoryJPA);
        verify(categoryRepositoryJPA).save(mockCategoryJPA);
    }

    @Test
    void shouldReturnIfExists() {
        when(categoryRepositoryJPA.existsById(4L)).thenReturn(true);
        assertTrue(categoryRepository.existsById(4L));

        when(categoryRepositoryJPA.existsById(2L)).thenReturn(false);
        assertFalse(categoryRepository.existsById(2L));
    }
}
