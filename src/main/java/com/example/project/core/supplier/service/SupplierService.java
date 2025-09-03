package com.example.project.core.supplier.service;

import com.example.project.core.supplier.model.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAll();
    Supplier getById(Long id);
    void delete(Supplier supplier);
    Supplier create(Supplier supplier);
    Supplier update(Supplier supplier);
}
