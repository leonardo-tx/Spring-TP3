package com.example.project.infra.supplier.repository;

import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.infra.supplier.persistence.SupplierJPA;
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
class SupplierRepositoryImplTest {
    @Mock
    private SupplierRepositoryJPA supplierRepositoryJPA;

    @Mock
    private Mapper<Supplier, SupplierJPA> supplierJPAMapper;

    @InjectMocks
    private SupplierRepositoryImpl supplierRepository;

    @Test
    void shouldFindAll() {
        List<SupplierJPA> entities = List.of(mock(SupplierJPA.class), mock(SupplierJPA.class));
        when(supplierRepositoryJPA.findAll()).thenReturn(entities);

        List<Supplier> categories = assertDoesNotThrow(() -> supplierRepository.findAll());

        assertEquals(entities.size(), categories.size());
        verify(supplierJPAMapper, times(entities.size())).toModel(any(SupplierJPA.class));
    }

    @Test
    void shouldFindById() {
        SupplierJPA mockSupplierJPA = mock(SupplierJPA.class);
        Supplier mockSupplier = mock(Supplier.class);

        when(supplierRepositoryJPA.findById(3L)).thenReturn(Optional.of(mockSupplierJPA));
        when(supplierJPAMapper.toModel(mockSupplierJPA)).thenReturn(mockSupplier);

        Optional<Supplier> optionalSupplier = assertDoesNotThrow(() -> supplierRepository.findById(3L));

        assertTrue(optionalSupplier.isPresent());
        assertSame(mockSupplier, optionalSupplier.get());

        verify(supplierJPAMapper, times(1)).toModel(mockSupplierJPA);
    }

    @Test
    void shouldReturnOptionEmptyWhenNotFindById() {
        when(supplierRepositoryJPA.findById(3L)).thenReturn(Optional.empty());

        Optional<Supplier> optionalSupplier = assertDoesNotThrow(() -> supplierRepository.findById(3L));

        assertTrue(optionalSupplier.isEmpty());

        verifyNoInteractions(supplierJPAMapper);
    }

    @Test
    void shouldDelete() {
        Supplier mockSupplier = mock(Supplier.class);
        when(mockSupplier.getId()).thenReturn(4L);

        assertDoesNotThrow(() -> supplierRepository.delete(mockSupplier));

        verify(supplierRepositoryJPA).deleteById(4L);
    }

    @Test
    void shouldSave() {
        Supplier mockSupplier = mock(Supplier.class);
        Supplier mockSavedSupplier = mock(Supplier.class);
        SupplierJPA mockSupplierJPA = mock(SupplierJPA.class);
        SupplierJPA mockSavedSupplierJPA = mock(SupplierJPA.class);

        when(supplierJPAMapper.toEntity(mockSupplier)).thenReturn(mockSupplierJPA);
        when(supplierRepositoryJPA.save(mockSupplierJPA)).thenReturn(mockSavedSupplierJPA);
        when(supplierJPAMapper.toModel(mockSavedSupplierJPA)).thenReturn(mockSavedSupplier);

        Supplier returnedSupplier = assertDoesNotThrow(() -> supplierRepository.save(mockSupplier));

        assertSame(mockSavedSupplier, returnedSupplier);
        verify(supplierJPAMapper, times(1)).toEntity(mockSupplier);
        verify(supplierJPAMapper, times(1)).toModel(mockSavedSupplierJPA);
        verify(supplierRepositoryJPA).save(mockSupplierJPA);
    }

    @Test
    void shouldReturnIfExists() {
        when(supplierRepositoryJPA.existsById(4L)).thenReturn(true);
        assertTrue(supplierRepository.existsById(4L));

        when(supplierRepositoryJPA.existsById(2L)).thenReturn(false);
        assertFalse(supplierRepository.existsById(2L));
    }
}
