package com.example.project.core.supplier.service;

import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.generic.exception.NotFoundException;
import com.example.project.core.supplier.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() ->
                new NotFoundException("supplier.not.found", "Could not find the supplier with the specified id.")
        );
    }

    @Override
    public void delete(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("The supplier must not be null.");
        }
        if (supplier.getId() == null) {
            throw new IllegalArgumentException("The supplier to delete must have an identifier.");
        }
        supplierRepository.delete(supplier);
    }

    @Override
    public Supplier create(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("The supplier must not be null.");
        }
        if (supplier.getId() != null) {
            throw new IllegalArgumentException("The supplier to create must not have an identifier.");
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier update(Supplier partialSupplier, Supplier targetSupplier) {
        // TODO
        return null;
    }
}
