package com.example.project.core.supplier.service;

import com.example.project.core.client.model.Client;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.supplier.repository.SupplierRepository;
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
class SupplierServiceImplTest {
    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Test
    void shouldReturnOnGetAll() {
        List<Supplier> suppliers = List.of(mock(Supplier.class), mock(Supplier.class));
        when(supplierRepository.findAll()).thenReturn(suppliers);

        List<Supplier> returnedSuppliers = supplierService.getAll();

        assertEquals(2, returnedSuppliers.size());
        assertSame(suppliers, returnedSuppliers);

        verify(supplierRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnOnGetById() {
        Supplier supplier = mock(Supplier.class);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        assertSame(supplier, supplierService.getById(1L));

        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowWhenNotFoundOnGetById() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        CoreException exception = assertThrows(NotFoundException.class, () -> supplierService.getById(1L));

        assertEquals("supplier.not.found", exception.getCode());
        assertEquals("Could not find the supplier with the specified id.", exception.getMessage());

        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteSupplier() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(2L);

        assertDoesNotThrow(() -> supplierService.delete(supplier));

        verify(supplierRepository, times(1)).delete(supplier);
    }

    @Test
    void shouldNotDeleteSupplierWithoutId() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> supplierService.delete(supplier)
        );

        assertEquals("The supplier to delete must have an identifier.", exception.getMessage());
        verifyNoInteractions(supplierRepository);
    }

    @Test
    void shouldCreateSupplier() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(null);
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier createdSupplier = assertDoesNotThrow(() -> supplierService.create(supplier));

        assertSame(supplier, createdSupplier);

        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void shouldNotCreateSupplierWithId() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(3L);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> supplierService.create(supplier)
        );

        assertEquals("The supplier to create must not have an identifier.", exception.getMessage());
        verifyNoInteractions(supplierRepository);
    }

    @Test
    void shouldUpdateSupplier() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(4L);
        when(supplierRepository.existsById(4L)).thenReturn(true);
        when(supplierRepository.save(supplier)).thenReturn(supplier);

        Supplier updatedSupplier = assertDoesNotThrow(() -> supplierService.update(supplier));

        assertSame(supplier, updatedSupplier);

        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void shouldNotUpdateSupplierWithoutId() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> supplierService.update(supplier)
        );

        assertEquals("The supplier to update must have an identifier.", exception.getMessage());
        verifyNoInteractions(supplierRepository);
    }

    @Test
    void shouldNotUpdateIfNotFound() {
        Supplier supplier = mock(Supplier.class);
        when(supplier.getId()).thenReturn(4L);
        when(supplierRepository.existsById(4L)).thenReturn(false);

        CoreException exception = assertThrows(NotFoundException.class, () -> supplierService.update(supplier));

        assertEquals("supplier.not.found", exception.getCode());
        assertEquals("Could not find the supplier with the specified id.", exception.getMessage());

        verifyNoMoreInteractions(supplierRepository);
    }
}
