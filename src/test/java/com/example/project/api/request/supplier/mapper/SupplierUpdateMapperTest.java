package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierUpdateDTO;
import com.example.project.core.supplier.factory.SupplierFactory;
import com.example.project.core.supplier.factory.SupplierParams;
import com.example.project.core.supplier.model.Supplier;
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
class SupplierUpdateMapperTest {
    @Mock
    private SupplierFactory supplierFactory;

    @InjectMocks
    private SupplierUpdateMapper supplierUpdateMapper;

    @Test
    void shouldMapSuccessfully() {
        SupplierUpdateDTO updateDTO = new SupplierUpdateDTO(7L, "Supplier", "21883763000106", "supplier@mail.com", "99999");

        when(supplierFactory.create(any(SupplierParams.class))).thenAnswer(invocation -> {
            SupplierParams params = invocation.getArgument(0);
            assertEquals(updateDTO.getId(), params.getId());
            assertEquals(updateDTO.getName(), params.getName());
            assertEquals(updateDTO.getDocument(), params.getDocument());
            assertEquals(updateDTO.getEmail(), params.getEmail());
            assertEquals(updateDTO.getPhone(), params.getPhone());

            return mock(Supplier.class);
        });

        Supplier result = supplierUpdateMapper.toModel(updateDTO);

        assertNotNull(result);
        verify(supplierFactory, times(1)).create(any(SupplierParams.class));
    }
}
