package com.example.project.infra.supplier.repository;

import com.example.project.infra.supplier.persistence.SupplierJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepositoryJPA extends JpaRepository<SupplierJPA, Long> {
}
