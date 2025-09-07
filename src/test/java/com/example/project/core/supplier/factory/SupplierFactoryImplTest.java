package com.example.project.core.supplier.factory;

import com.example.project.core.generic.model.EntityType;
import com.example.project.core.supplier.model.Supplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SupplierFactoryImplTest {
    @InjectMocks
    private SupplierFactoryImpl supplierFactory;

    @Test
    void shouldCreate() {
        SupplierParams params = new SupplierParams(
                8L,
                "Supplier Y",
                "85620219000146",
                "abc@supplier.com.br",
                "5588"
        );
        Supplier supplier = supplierFactory.create(params);

        assertEquals(params.getId(), supplier.getId());
        assertEquals(params.getName(), supplier.getName().getValue());
        assertEquals(params.getDocument(), supplier.getDocument().getValue());
        assertEquals(EntityType.LEGAL, supplier.getDocument().getType());
        assertEquals(params.getEmail(), supplier.getEmail().getValue());
        assertEquals(params.getPhone(), supplier.getPhone().getValue());
    }
}
