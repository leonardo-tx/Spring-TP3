package com.example.project.infra.supplier.mapper;

import com.example.project.core.generic.model.*;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.infra.supplier.persistence.SupplierJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SupplierJPAMapperTest {
    @InjectMocks
    private SupplierJPAMapper clientJPAMapper;

    @Test
    void shouldMapToModel() {
        SupplierJPA entity = SupplierJPA.builder()
                .id(1L)
                .name("Fornecedor")
                .document("88035950000100")
                .email("supplier@empresa.com")
                .phone("99993")
                .build();
        Supplier model = clientJPAMapper.toModel(entity);

        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName().getValue());
        assertEquals(entity.getDocument(), model.getDocument().getValue());
        assertEquals(EntityType.LEGAL, model.getDocument().getType());
        assertEquals(entity.getEmail(), model.getEmail().getValue());
        assertEquals(entity.getPhone(), model.getPhone().getValue());
    }

    @Test
    void shouldMapToEntity() {
        Supplier model = new Supplier(
                1L,
                EntityName.fromInfra("Fornecedor"),
                EntityDocument.fromInfra("88035950000100"),
                Email.fromInfra("supplier@empresa.com"),
                Phone.fromInfra("99993")
        );
        SupplierJPA entity = clientJPAMapper.toEntity(model);

        assertEquals(model.getId(), entity.getId());
        assertEquals(model.getName().getValue(), entity.getName());
        assertEquals(model.getDocument().getValue(), entity.getDocument());
        assertEquals(model.getEmail().getValue(), entity.getEmail());
        assertEquals(model.getPhone().getValue(), entity.getPhone());
    }
}
