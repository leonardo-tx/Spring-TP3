package com.example.project.core.supplier.repository;

import com.example.project.core.supplier.model.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository {
    List<Supplier> findAll();
    Optional<Supplier> findById(Long id);
    boolean existsById(Long id);
    void delete(Supplier supplier);
    Supplier save(Supplier supplier);
}
