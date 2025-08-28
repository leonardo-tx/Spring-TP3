package com.example.project.infra.supplier.repository;

import com.example.project.core.supplier.model.Supplier;
import com.example.project.core.generic.mapper.Mapper;
import com.example.project.core.supplier.repository.SupplierRepository;
import com.example.project.infra.supplier.persistence.SupplierJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SupplierRepositoryImpl implements SupplierRepository {
    private final SupplierRepositoryJPA supplierRepositoryJPA;
    private final Mapper<Supplier, SupplierJPA> supplierJPAMapper;

    @Override
    public List<Supplier> findAll() {
        return supplierRepositoryJPA.findAll()
                .stream()
                .map(supplierJPAMapper::toModel)
                .toList();
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierRepositoryJPA.findById(id).map(supplierJPAMapper::toModel);
    }

    @Override
    public void delete(Supplier supplier) {
        supplierRepositoryJPA.deleteById(supplier.getId());
    }

    @Override
    public Supplier save(Supplier supplier) {
        SupplierJPA supplierJPA = supplierJPAMapper.toEntity(supplier);
        SupplierJPA createdSupplierJPA = supplierRepositoryJPA.save(supplierJPA);

        return supplierJPAMapper.toModel(createdSupplierJPA);
    }
}
