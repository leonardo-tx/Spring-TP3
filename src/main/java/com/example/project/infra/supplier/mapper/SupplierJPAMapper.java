package com.example.project.infra.supplier.mapper;

import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.supplier.model.Supplier;
import com.example.project.infra.supplier.persistence.SupplierJPA;
import org.springframework.stereotype.Component;

@Component
public class SupplierJPAMapper implements Mapper<Supplier, SupplierJPA> {
    @Override
    public Supplier toModel(SupplierJPA entity) {
        return null;
    }

    @Override
    public SupplierJPA toEntity(Supplier model) {
        return null;
    }
}
