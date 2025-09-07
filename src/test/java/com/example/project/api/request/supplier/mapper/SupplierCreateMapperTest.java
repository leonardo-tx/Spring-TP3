package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierCreateDTO;
import com.example.project.core.supplier.factory.SupplierParams;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.supplier.factory.SupplierFactory;
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
class SupplierCreateMapperTest {
    @Mock
    private SupplierFactory supplierFactory;

    @InjectMocks
    private SupplierCreateMapper supplierCreateMapper;

    @Test
    void shouldMapSuccessfully() {
        SupplierCreateDTO createDTO = new SupplierCreateDTO("Supplier", "21883763000106", "supplier@mail.com", "99999");

        when(supplierFactory.create(any(SupplierParams.class))).thenAnswer(invocation -> {
            SupplierParams params = invocation.getArgument(0);
            assertNull(params.getId());
            assertEquals(createDTO.getName(), params.getName());
            assertEquals(createDTO.getDocument(), params.getDocument());
            assertEquals(createDTO.getEmail(), params.getEmail());
            assertEquals(createDTO.getPhone(), params.getPhone());

            return mock(Supplier.class);
        });

        Supplier result = supplierCreateMapper.toModel(createDTO);

        assertNotNull(result);
        verify(supplierFactory, times(1)).create(any(SupplierParams.class));
    }
}
