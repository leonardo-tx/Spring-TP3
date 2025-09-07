package com.example.project.api.response.supplier.mapper;

import com.example.project.api.response.supplier.dto.SupplierViewDTO;
import com.example.project.core.generic.model.*;
import com.example.project.core.supplier.model.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SupplierViewMapperTest {
    @InjectMocks
    private SupplierViewMapper supplierViewMapper;

    @Test
    void shouldParseSuccessfully() {
        Supplier supplier = new Supplier(
                5L,
                EntityName.fromInfra("Tech Supplies Inc"),
                EntityDocument.fromInfra("91654943000193"),
                Email.fromInfra("contact@techsupplies.com"),
                Phone.fromInfra("55556")
        );

        SupplierViewDTO result = supplierViewMapper.toEntity(supplier);

        assertNotNull(result);
        assertEquals(supplier.getId(), result.getId());
        assertEquals(supplier.getName().getValue(), result.getName());
        assertEquals(supplier.getDocument().getValue(), result.getDocument());
        assertEquals(EntityType.LEGAL, result.getEntityType());
        assertEquals(supplier.getEmail().getValue(), result.getEmail());
        assertEquals(supplier.getPhone().getValue(), result.getPhone());
    }
}
