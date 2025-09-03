package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierCreateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.supplier.factory.SupplierFactory;
import com.example.project.core.supplier.factory.SupplierParams;
import com.example.project.core.supplier.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierCreateMapper implements InputMapper<Supplier, SupplierCreateDTO> {
    private final SupplierFactory supplierFactory;

    @Override
    public Supplier toModel(SupplierCreateDTO entity) {
        SupplierParams params = new SupplierParams(
                null,
                entity.getName(),
                entity.getDocument(),
                entity.getEmail(),
                entity.getPhone()
        );
        return supplierFactory.create(params);
    }
}
