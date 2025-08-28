package com.example.project.api.response.supplier.mapper;

import com.example.project.api.response.supplier.dto.SupplierViewDTO;
import com.example.project.core.generic.mapper.OutputMapper;
import com.example.project.core.supplier.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierViewMapper implements OutputMapper<Supplier, SupplierViewDTO> {
    @Override
    public SupplierViewDTO toEntity(Supplier model) {
        return null;
    }
}
