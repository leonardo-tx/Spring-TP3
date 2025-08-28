package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierUpdateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.supplier.model.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierUpdateMapper implements InputMapper<Supplier, SupplierUpdateDTO> {
    @Override
    public Supplier toModel(SupplierUpdateDTO entity) {
        return null;
    }
}
