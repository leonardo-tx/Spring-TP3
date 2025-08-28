package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierCreateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.supplier.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierCreateMapper implements InputMapper<Supplier, SupplierCreateDTO> {
    @Override
    public Supplier toModel(SupplierCreateDTO entity) {
        return null;
    }
}
