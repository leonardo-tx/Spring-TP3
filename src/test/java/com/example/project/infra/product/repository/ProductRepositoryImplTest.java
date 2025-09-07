package com.example.project.infra.product.repository;

import com.example.project.core.product.model.Product;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.product.persistence.ProductJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {
    @Mock
    private ProductRepositoryJPA productRepositoryJPA;

    @Mock
    private Mapper<Product, ProductJPA> productJPAMapper;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    @Test
    void shouldFindAll() {
        List<ProductJPA> entities = List.of(mock(ProductJPA.class), mock(ProductJPA.class));
        when(productRepositoryJPA.findAll()).thenReturn(entities);

        List<Product> categories = assertDoesNotThrow(() -> productRepository.findAll());

        assertEquals(entities.size(), categories.size());
        verify(productJPAMapper, times(entities.size())).toModel(any(ProductJPA.class));
    }

    @Test
    void shouldFindById() {
        ProductJPA mockProductJPA = mock(ProductJPA.class);
        Product mockProduct = mock(Product.class);

        when(productRepositoryJPA.findById(3L)).thenReturn(Optional.of(mockProductJPA));
        when(productJPAMapper.toModel(mockProductJPA)).thenReturn(mockProduct);

        Optional<Product> optionalProduct = assertDoesNotThrow(() -> productRepository.findById(3L));

        assertTrue(optionalProduct.isPresent());
        assertSame(mockProduct, optionalProduct.get());

        verify(productJPAMapper, times(1)).toModel(mockProductJPA);
    }

    @Test
    void shouldReturnOptionEmptyWhenNotFindById() {
        when(productRepositoryJPA.findById(3L)).thenReturn(Optional.empty());

        Optional<Product> optionalProduct = assertDoesNotThrow(() -> productRepository.findById(3L));

        assertTrue(optionalProduct.isEmpty());

        verifyNoInteractions(productJPAMapper);
    }

    @Test
    void shouldDelete() {
        Product mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(4L);

        assertDoesNotThrow(() -> productRepository.delete(mockProduct));

        verify(productRepositoryJPA).deleteById(4L);
    }

    @Test
    void shouldSave() {
        Product mockProduct = mock(Product.class);
        Product mockSavedProduct = mock(Product.class);
        ProductJPA mockProductJPA = mock(ProductJPA.class);
        ProductJPA mockSavedProductJPA = mock(ProductJPA.class);

        when(productJPAMapper.toEntity(mockProduct)).thenReturn(mockProductJPA);
        when(productRepositoryJPA.save(mockProductJPA)).thenReturn(mockSavedProductJPA);
        when(productJPAMapper.toModel(mockSavedProductJPA)).thenReturn(mockSavedProduct);

        Product returnedProduct = assertDoesNotThrow(() -> productRepository.save(mockProduct));

        assertSame(mockSavedProduct, returnedProduct);
        verify(productJPAMapper, times(1)).toEntity(mockProduct);
        verify(productJPAMapper, times(1)).toModel(mockSavedProductJPA);
        verify(productRepositoryJPA).save(mockProductJPA);
    }

    @Test
    void shouldReturnIfExists() {
        when(productRepositoryJPA.existsById(4L)).thenReturn(true);
        assertTrue(productRepository.existsById(4L));

        when(productRepositoryJPA.existsById(2L)).thenReturn(false);
        assertFalse(productRepository.existsById(2L));
    }
}
