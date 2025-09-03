package com.example.project.infra.supplier.mapper;

import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.generic.model.Email;
import com.example.project.core.generic.model.EntityDocument;
import com.example.project.core.generic.model.EntityName;
import com.example.project.core.generic.model.Phone;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.infra.supplier.persistence.SupplierJPA;
import org.springframework.stereotype.Component;

@Component
public class SupplierJPAMapper implements Mapper<Supplier, SupplierJPA> {
    @Override
    public Supplier toModel(SupplierJPA entity) {
        return new Supplier(
                entity.getId(),
                EntityName.fromInfra(entity.getName()),
                EntityDocument.fromInfra(entity.getDocument()),
                Email.fromInfra(entity.getEmail()),
                Phone.fromInfra(entity.getPhone())
        );
    }

    @Override
    public SupplierJPA toEntity(Supplier model) {
        return SupplierJPA.builder()
                .id(model.getId())
                .name(model.getName().getValue())
                .document(model.getDocument().getValue())
                .email(model.getEmail().getValue())
                .phone(model.getPhone().getValue())
                .build();
    }
}
