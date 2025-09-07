package com.example.project.core.product.service;

import com.example.project.core.client.model.Client;
import com.example.project.core.product.model.Product;
import com.example.project.core.product.repository.ProductRepository;
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
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldReturnOnGetAll() {
        List<Product> products = List.of(mock(Product.class), mock(Product.class));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> returnedProducts = productService.getAll();

        assertEquals(2, returnedProducts.size());
        assertSame(products, returnedProducts);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOnGetById() {
        Product product = mock(Product.class);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertSame(product, productService.getById(1L));

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenNotFoundOnGetById() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        CoreException exception = assertThrows(NotFoundException.class, () -> productService.getById(1L));

        assertEquals("product.not.found", exception.getCode());
        assertEquals("Could not find the product with the specified id.", exception.getMessage());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteProduct() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(2L);

        assertDoesNotThrow(() -> productService.delete(product));

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void shouldNotDeleteProductWithoutId() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.delete(product)
        );

        assertEquals("The product to delete must have an identifier.", exception.getMessage());
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldCreateProduct() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(null);
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = assertDoesNotThrow(() -> productService.create(product));

        assertSame(product, createdProduct);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldNotCreateProductWithId() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(3L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.create(product)
        );

        assertEquals("The product to create must not have an identifier.", exception.getMessage());
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldUpdateProduct() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(4L);
        when(productRepository.existsById(4L)).thenReturn(true);
        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = assertDoesNotThrow(() -> productService.update(product));

        assertSame(product, updatedProduct);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldNotUpdateProductWithoutId() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.update(product)
        );

        assertEquals("The product to update must have an identifier.", exception.getMessage());
        verifyNoInteractions(productRepository);
    }

    @Test
    void shouldNotUpdateIfNotFound() {
        Product product = mock(Product.class);
        when(product.getId()).thenReturn(4L);
        when(productRepository.existsById(4L)).thenReturn(false);

        CoreException exception = assertThrows(NotFoundException.class, () -> productService.update(product));

        assertEquals("product.not.found", exception.getCode());
        assertEquals("Could not find the product with the specified id.", exception.getMessage());

        verifyNoMoreInteractions(productRepository);
    }
}
