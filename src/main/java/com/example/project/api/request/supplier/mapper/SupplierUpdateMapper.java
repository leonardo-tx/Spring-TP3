package com.example.project.api.request.supplier.mapper;

import com.example.project.api.request.supplier.dto.SupplierUpdateDTO;
import com.example.project.core.generic.mapper.InputMapper;
import com.example.project.core.supplier.factory.SupplierFactory;
import com.example.project.core.supplier.factory.SupplierParams;
import com.example.project.core.supplier.model.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SupplierUpdateMapper implements InputMapper<Supplier, SupplierUpdateDTO> {
    private final SupplierFactory supplierFactory;

    @Override
    public Supplier toModel(SupplierUpdateDTO entity) {
        SupplierParams params = new SupplierParams(
                entity.getId(),
                entity.getName(),
                entity.getDocument(),
                entity.getEmail(),
                entity.getPhone()
        );
        return supplierFactory.create(params);
    }
}
