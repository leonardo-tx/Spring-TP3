package com.example.project.infra.product.repository;

import com.example.project.infra.product.persistence.ProductJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositoryJPA extends JpaRepository<ProductJPA, Long> {
}
